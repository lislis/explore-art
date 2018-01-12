(ns app.noise)

;; https://github.com/quil/quil/blob/master/src/clj/quil/helpers/calc.clj#L3
(defn mul-add
  "Generate a potential lazy sequence of values which is the result of
   multiplying each s by mul and then adding add. s mul and add may be
   seqs in which case the result will also be seq with the length
   being the same as the shortest input seq (similar to the behaviour
   of map). If all the seqs passed are infinite lazy seqs, the result
   will also be infinite and lazy..
   (mul-add 2 2 1)           ;=> 5
   (mul-add [2 2] 2 1)       ;=> [5 5]
   (mul-add [2 2] [2 4 6] 1) ;=> [5] 9
  (mul-add (range) 2 1)     ;=> [1 3 5 7 9 11 13...] ;; infinite seq
  (mul-add (range) [2 2] 1) ;=> [1 3]"
  [s mul add]
  (if (and (number? mul) (number? add) (number? s))
    (+ add (* mul s))
    (let [[mul nxt-mul] (if (sequential? mul)
                          [(first mul) (next mul)]
                          [mul mul])
          [add nxt-add] (if (sequential? add)
                          [(first add) (next add)]
                          [add add])
          [s nxt-s]     (if (sequential? s)
                          [(first s) (next s)]
                          [s s])]
      (lazy-seq
       (cons (+ add (* mul s)) (if (and nxt-mul nxt-add nxt-s)
                                 (mul-add  nxt-s nxt-mul nxt-add)
                                 []))))))


;; Stuff with Perlin noise
;; example setup
;
; (defonce state
;   (atom {:xoff 0.0 :yoff 10000.0}))
;
; (defn setup []
;   (js/createCanvas width height))
;
; (defn draw []
;   (js/loadPixels)
;   (js/noiseDetail 6 0.25)
;   (traverse-pixels width height)
;   (js/updatePixels))
;

(defn traverse-pixels [width height]
  (let [x-start 0
        y-start 10000]
       (dorun
        (for [y (range height)
              x (range width)]
          (let [xoff (mul-add x 0.009 x-start)
                yoff (mul-add y 0.009 y-start)]
            (js/set x y (js/map (js/noise xoff yoff) 0 1 0 255)))))))
