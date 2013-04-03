((arp-gen 500 '((0) () (5) (7) (0) (-2) (0) ())) (fn [note] (amped-sin-wave (note-to-pitch (+ note 57)) 0.01 0.89 0.1 0.4)))

((arp-gen 500 '((0 5 7 12) () (0 7))) (fn [note] (snare (note-to-pitch (+ note 57)))))

((arp-gen 250 '(() (0) () (12) () (24) () (36))) (fn [note] (kick (note-to-pitch (+ note 33)) )))

((arp-gen 250 '((0) (0) (5) (12) (7) (3) (5) (0) (0) (1) (3) (5) (7) (3) (0) ())) (fn [note] (amped-perc-sin (note-to-pitch (+ note 69)) )))


;;; weird space-age sound
(demo (amp-power-chord (* (env-gen (perc 0.01 5.99) 1 1 0 1 FREE) (sin-osc (+ (* (sin-osc 10) (perc 0.01 5.99) 50) 440)))))