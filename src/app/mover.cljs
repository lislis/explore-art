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

(defn seed [num]
  (for [i (range num)]
    (let [m (create (* 30 i) 50 0 0 0 0 2)]
      m)))

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

; I don't think that works
(defn updates-random-acceleration [mover width height]
  (let [st mover
        rand-a (vector/random2d)
        a (vector/mult rand-a 0.2)
        calc-v (vector/add (:velocity st) a)
        v (vector/limit calc-v (:topspeed st))
        calc-l (vector/add (:location st) v)
        l (wrap-edges calc-l width height)]
    (create-vec l v a (:topspeed mover))))


;; I don't think this works either
(defn updates-perlin-acceleration [mover width height xoff yoff]
  (let [st mover
        rand-a (vector/create (js/map (js/noise xoff) 0 1 -1 1) (js/map(js/noise yoff) 0 1 -1 1))
        a (vector/mult rand-a 0.03)
        calc-v (vector/add (:velocity st) a)
        v (vector/limit calc-v (:topspeed st))
        calc-l (vector/add (:location st) v)
        l (wrap-edges calc-l width height)]
    (create-vec l v a (:topspeed mover))))


(defn apply-acceleration [mover acc]
  (let [v (vector/limit (vector/add (:velocity mover) acc) (:topspeed mover))
        l (vector/add (:location mover) v)]
    (create-vec l v acc (:topspeed mover))))

(defn accelerate-to-mouse [mover]
  (let [m-v (vector/create js/mouseX js/mouseY)
        loc (:location mover)
        dir (vector/mult (vector/normalize (vector/sub m-v loc)) 0.5)
        acc dir]
    (apply-acceleration mover acc)))

;; were used together with key press
(defn accelerate []
  (let [x (:x (:acceleration (:mover @state)))
        new-x (+ x 0.01)]
    (swap! state assoc-in [:mover :acceleration] {:x new-x})))

(defn decelerate []
  (let [x (:x (:acceleration (:mover @state)))
        new-x (- x 0.01)]
    (swap! state assoc-in [:mover :acceleration] {:x new-x})))
