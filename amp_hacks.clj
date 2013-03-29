
;; non-linear amplifier experiments

(defmacro amp-power-chord
  "Non-linear 'amplifier' which turns a pure wav at amplitude 1 into
   that same waveform, plus a 'power chord' (base, fifth octave) one octave
   higher than the original note."
  [x]
  `(overtone.sc.ugen-collide/*
    0.5 ~x
    (overtone.sc.ugen-collide/+
     -1 (overtone.sc.ugen-collide/*
         ~x (overtone.sc.ugen-collide/+
             -3 (overtone.sc.ugen-collide/*
                 ~x (overtone.sc.ugen-collide/+
                     2 (overtone.sc.ugen-collide/* ~x 4)))))))
  ; Horner polynomials are so ugly in lisp
  )

(defmacro amp-even-harmonics
  "Non-linear 'amplifier' which turns a pure wav at amplitude 1 into
   that same waveform, plus some of it's even harmonics"
  [x]
  `(overtone.sc.ugen-collide/+
    1 (overtone.sc.ugen-collide/*
       ~x (overtone.sc.ugen-collide/+
           1 (overtone.sc.ugen-collide/*
              ~x ~x (overtone.sc.ugen-collide/+
                     8 (overtone.sc.ugen-collide/*
                        ~x ~x (overtone.sc.ugen-collide/+
                               -40.0 (overtone.sc.ugen-collide/*
                                      ~x ~x 32.0))))))))
  ; see comment from previous amp
  )

(definst amped-sin-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (amp-power-chord
   (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
      (sin-osc freq)
      vol))
  )
;; end of non-linear amp experiments

