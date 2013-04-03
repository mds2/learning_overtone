
;; non-linear amplifier experiments

; 0.5 * x * (-1 + x * (-3 + x * ( 2 + 4x)))
; -0.5 * x - 1.5 * x ^2 + x ^ 3 + 2 * x ^ 4
(defn amp-power-chord
  "Non-linear 'amplifier' which turns a pure wav at amplitude 1 into
   that same waveform, plus a 'power chord' (base, fifth octave) one octave
   higher than the original note."
  [x]
  (overtone.sc.ugen-collide/*
   0.5 x
   (overtone.sc.ugen-collide/+
    -1 (overtone.sc.ugen-collide/*
        x (overtone.sc.ugen-collide/+
           -3 (overtone.sc.ugen-collide/*
               x (overtone.sc.ugen-collide/+
                  2 (overtone.sc.ugen-collide/* x 4)))))))
  ; Horner polynomials are so ugly in lisp
  )

(defn amp-even-harmonics
  "Non-linear 'amplifier' which turns a pure wav at amplitude 1 into
   that same waveform, plus some of it's even harmonics"
  [x]
  (overtone.sc.ugen-collide/+
   1 (overtone.sc.ugen-collide/*
      x (overtone.sc.ugen-collide/+
         1 (overtone.sc.ugen-collide/*
            (squared x)
            (overtone.sc.ugen-collide/+
             8 (overtone.sc.ugen-collide/*
                (squared x)
                (overtone.sc.ugen-collide/+
                 -40.0 (overtone.sc.ugen-collide/*
                        (squared x)32.0))))))))
  ; see comment from previous amp
  )

(definst amped-sin-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (amp-power-chord
   (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
      (sin-osc freq)
      vol))
  )

(definst amped-perc-sin [freq 220 attack 0.01 release 1.0 vol 0.4]
  (amp-power-chord
   (* (env-gen (perc attack release ) 1 1 0 1 FREE)
      (+ (sin-osc freq)
         (sin-osc (+ freq 1)))
      vol))
  )

(defmacro
; The following does not work.
;; (definst wierdo [freq 440]
;;   (amp-power-chord 
;;    (* (env-gen (perc 0.01 5.99) 1 1 0 1 FREE)
;;       (overtone.sc.ugen-collide/+
;;        (sin-osc (overtone.sc.ugen-collide/+
;;                  (overtone.sc.ugen-collide/*
;;                   (sin-osc 10) (perc 0.01 5.99) 50) freq))
;;        (sin-osc (fifth freq)))
;;       0.5) )
;; )

;; end of non-linear amp experiments

