(ns learning-overtone.fibo)
;; generalized fibonacci stuff

(defn fib4
  [combiner a b c d n]
  (if (< n 1) a
      (recur combiner (combiner c d) a b c (- n 1))))
