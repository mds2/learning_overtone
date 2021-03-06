(ns learning-overtone.instruments)

(use 'overtone.core)

;; From the overtone help pages
;; https://github.com/overtone/overtone/wiki/Oscillators

(definst sin-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (sin-osc freq)
     vol))

(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

(definst square-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-pulse freq)
     vol))

(definst noisey [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (pink-noise) ; also have (white-noise) and others...
     vol))

(definst triangle-wave [freq 440 attack 0.01 sustain 0.1 release 0.4 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-tri freq)
     vol))
;; end of "copied from help" stuff

;; no longer really copied from help.
(definst snare [freq 110 attack 0.01 release 0.49 vol 0.5] 
  (* (env-gen (perc attack release) 1 1 0 1 FREE)
     (pink-noise)
     vol))

(definst kick [freq 110 attack 0.01 release 0.49 vol 0.5] 
  (* (env-gen (perc attack release) 1 1 0 1 FREE)
     (brown-noise)
     (sin-osc freq)
     vol))


