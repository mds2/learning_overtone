
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

(definst amped-perc-multi-tri [freq 110 attack 0.01 release 1.0 vol 0.4]
  (amp-power-chord
   (* (env-gen (perc attack release ) 1 1 0 1 FREE)
      (+ (lf-tri freq)
         (lf-tri (+ freq 1))
         (lf-tri (+ freq 2))
         (lf-tri (+ freq 3))
         (lf-tri (+ freq 4))
         (lf-tri (+ freq 5))
         (lf-tri (+ freq 6))
         (lf-tri (+ freq 7))
         (lf-tri (+ freq 8))
         (lf-tri (+ freq 9))
         )
      0.1
      vol))
  )
;; end of non-linear amp experiments

