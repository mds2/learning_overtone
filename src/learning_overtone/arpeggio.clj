(ns learning-overtone.arpeggio)

(use 'overtone.core)
(use 'learning-overtone.instruments)
(use 'learning-overtone.amplifiers)


(defn note-to-pitch
  "Does what it says"
  [note]
  (* 220 (Math/pow 2.0 (* (- note 57) (/ 1.0 12.0)))))


(defn pitch-inst-gen
  "Creates a function that plays an [inst] tone in key (base) when given
   a 12-tone scale integer argument"
  [inst base & args]
  (fn [note] (apply inst (note-to-pitch (+ note base)) args))
  )

(defn chord-gen
  "Turns a generated instrument into one that plays chords"
  [inst chord-note-list]
  (fn [note] (doall (map (comp inst #(+ note %)) chord-note-list)))
  )


(defn arp-gen
  "Takes a sequence of (possibly empty!) lists of notes, and plays
   each list as a chord at regularly spaced time intervals of 'beat-duration'."
  [beat-duration sequence]
  (let [period (* beat-duration (count sequence))]
    (fn [gen-inst]
      (loop
          [sequence sequence
           offset 0
           acc '()]
        (if
            (empty? sequence)
          (flatten acc)
          (recur (rest sequence) (+ offset beat-duration)
                 (conj acc
                       (periodic period
                                 (fn []
                                   (doall (map gen-inst (first sequence)))
                                   )
                                 offset)
                       )
                 )
          )
        )))
  )

(def arp-clave (arp-gen 250 '((0 5) () () (0 7) () () (0 12) ())))
(def sin-chord (chord-gen (pitch-inst-gen sin-wave 57 0.05 0.1 0.35) '(0 5 7 12)))
(def alt-snare (pitch-inst-gen noisey 10 0.05 0.1 0.35))
(def arp-nines (arp-gen 250 '((0) () () (7) () () (0) () ())))
(def arp-oompa (arp-gen 250 '((0 0 5 7 12) (12 17))))
(def arp-steady (arp-gen 500 '((0))))

