(ns app.mover
  (:require [app.vector :as vector]))

;; mover functions that implement vectors
;; example wrapping edge accelerating ball
;;
;; (defonce state
;;   (atom {:mover (mover/create 400 200 0 0 -0.001 0.01 8)}))
;;
;; (defn draw []
;;   (let [updated-mover (mover/update (:mover @state) state width height)]
;;     (swap! state assoc :mover updated-mover) ; emulates update()
;;     (vector/draw (:location (:mover @state)))))

(defn create-vec [l v a topspeed]
  {:location l
   :velocity v
   :acceleration a
   :topspeed topspeed})

(defn create [x y vx vy ax ay topspeed]
  (create-vec
   (vector/create x y)
   (vector/create vx vy)
   (vector/create ax ay)
   topspeed))

(defn wrap-edges [vec width height]
  (let [x (cond (> (:x vec) width) 0
                (< (:x vec) 0) width
                :else (:x vec))
        y (cond (> (:y vec) height) 0
                (< (:y vec) 0) height
                :else (:y vec))]
    (vector/create x y)))

(defn updates [mover width height]
  (let [st mover
        a (:acceleration st)
        calc-v (vector/add (:velocity st) a)
        v (vector/limit calc-v (:topspeed st))
        calc-l (vector/add (:location st) v)
        l (wrap-edges calc-l width height)]
    (create-vec l v a (:topspeed mover))))

(defn updates-random-acceleration [mover width height]
  (let [st mover
        rand-a (vector/random2d)
        a (vector/mult rand-a 0.2)
        calc-v (vector/add (:velocity st) a)
        v (vector/limit calc-v (:topspeed st))
        calc-l (vector/add (:location st) v)
        l (wrap-edges calc-l width height)]
    ;;(js/console.log (:x a) (:y a))
    (create-vec l v a (:topspeed mover))))

(defn updates-perlin-acceleration [mover width height xoff yoff]
  (let [st mover
        rand-a (vector/create (js/noise xoff) (js/noise yoff))
        a (vector/mult rand-a 0.03)
        calc-v (vector/add (:velocity st) a)
        v (vector/limit calc-v (:topspeed st))
        calc-l (vector/add (:location st) v)
        l (wrap-edges calc-l width height)]
    ;(js/console.log (:x a) (:y a))
    ;;(js/console.log (:x a) (:y a))
    (create-vec l v a (:topspeed mover))))
