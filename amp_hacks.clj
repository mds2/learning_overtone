
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

(definst amped-sin-wave-undertone [freq 440 undertone 110 attack 0.01
                                   sustain 0.4 release 0.1 vol 0.4] 
  (amp-power-chord
   (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
      (+ (sin-osc freq)
         (* (sin-osc undertone) 0.1))
      vol))
  )

(definst amped-perc-sin [freq 220 attack 0.01 release 1.0 vol 0.4]
  (amp-power-chord
   (* (env-gen (perc attack release ) 1 1 0 1 FREE)
      (+ (sin-osc freq)
         (sin-osc (+ freq 1)))
      vol))
  )

(definst amped-kick [freq 110 attack 0.01 release 0.49 vol 0.5] 
  (* (env-gen (perc attack release) 1 1 0 1 FREE)
     (amp-power-chord (* (brown-noise)
                         (sin-osc freq)
                         vol )
                      )
     ))


;; (demo (amp-power-chord (+ (* (sin-osc 440) (exp (* 10 (neg (squared (- (line) 0.25)))))) (* (sin-osc (* (Math/pow 2.0 (/ 3.0 12.0)) 440)) (exp (* 10 (neg (squared (- (line) 0.75)))))))))

;; (defmacro def-perverse [fname args & body]
;; `(do (defn ~fname ~args ~@body)
;; (defn ~(symbol (str fname "-mod")) ~args ~@(rest body))))

;; (defn melo-to-amped-ugen [freq melody time]
;;      (amp-power-chord
;;       (loop [melody melody curtime 0.0 accum 1.0]
;;           (if (empty? melody)
;;             accum
;;             (recur
;;              (rest melody) (+ curtime time)
;;              (overtone.sc.ugen-collide/+
;;               accum
;;               (overtone.sc.ugen-collide/* 
;;                (sin-osc (* freq (Math/pow (/ 2.0 12.0) (first melody) )))
;;                (exp
;;                 (* 10 (neg (squared (- (line) (* 0.001 curtime))))))
;;                )
;;               )
;;              )
;;             )
;;           )
;;       )
;;      )

;; (defmacro make-amp-melo [inst-name melody time]
;;   `(definst ~inst-name [freq 220 vol 0.4]
;;      (melo-to-amped-ugen freq melody time)
;;      )
;;   )

;; ; (make-amp-melo test-melo-inst '(0 5 7) 250)
;; (println (macroexpand (make-amp-melo test-melo-inst-macro '(0 5 7) 250)))

;(defmacro
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

