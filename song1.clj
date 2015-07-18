(load-file "learning_overtone/arp_hacks.clj")
(load-file "learning_overtone/fibo.clj")

(defn chord1 [note] (cons note '()))
(defn chord2 [note]
  (if (or (= note 5) (= note 7)) (cons note '(12))
      (if (= note 2) (cons note '(9))
          (if (= note 9) (cons note '(14))
              (if (= note 0) '(0 7 12)
                  (cons note '()))))))
(defn chord3 [note]
  (if (or (= note 5) (= note 7)) (cons note '(0))
      (if (= note 2) (cons note '(-5))
          (if (= note 9) (cons note '(2))
              (if (= note 0) '(0 5 -7)
                  (cons note '()))))))


(defn start-song
  [song-file]
  (if (not (empty? song-file))
    (recording-start song-file))
  (def melody-chords
    ((arp-gen 250 (fib4 concat
                        (chord1 '(0 7 5))
                        (chord2 '(0 2 5 10))
                        (chord3 '(0 4 7 12))
                        (chord2 '(12 5 7 0))
                        14))
     (fn [note] (amped-sin-wave (note-to-pitch (+ note 57))
                                0.01 0.89 0.1 0.1))))
  (def melody-notes
    ((arp-gen 250 (fib4 concat
                        (chord1 '(0 7 5))
                        (chord1 '(0 2 5 10))
                        (chord1 '(0 4 7 12))
                        (chord1 '(12 5 7 0))
                        14))
     (fn [note] (amped-perc-sin (note-to-pitch (+ note 69))))))
  (def melody-drum
    ((arp-gen 250 (fib4 concat
                        '((0 7 12) () ())
                        '((0) () (0 5) ())
                        '(() (7) () (0))
                        '((12 5) (7 0) () ())
                        14))
     (fn [note] (kick (note-to-pitch (+ note 33)))))))


(defn stop-melody []
  (doall (map (comp kill-player :id)  melody-notes)))

(defn stop-chords []
  (doall (map (comp kill-player :id)  melody-chords)))

(defn stop-drum []
  (doall (map (comp kill-player :id)  melody-drum)))

(defn stop-song []
  (do
    (doall (map (comp kill-player :id) melody-chords))
    (doall (map (comp kill-player :id) melody-notes))
    (doall (map (comp kill-player :id) melody-drum))
    (recording-stop)))






