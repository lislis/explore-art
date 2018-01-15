(ns app.main
  (:require [app.walker :as walker]
            [app.distribution :as dist]
            [app.noise :as noise]
            [app.vector :as vector]))

(def width 600)
(def height 400)

(defn create-mover-vec [l v a]
  {:location l
   :velocity v
   :acceleration a})

(defn create-mover [x y vx vy ax ay]
  (create-mover-vec
   (vector/create x y)
   (vector/create vx vy)
   (vector/create ax ay)))

(defonce state
  (atom {:mover (create-mover 400 200 0 0 -0.001 0.01)}))

(defn setup []
  (js/createCanvas width height))

(defn draw []
  (let [st (:mover @state)
        a (:acceleration st)
        v (vector/add (:velocity st) a)
        l (vector/add (:location st) v)]
    (swap! state assoc :mover (create-mover-vec l v a))
    (vector/draw l)))

;; start stop pattern as described in
;; https://github.com/thheller/shadow-cljs/wiki/ClojureScript-for-the-browser
(defn start []
  (doto js/window
    (aset "setup" setup)
    (aset "draw" draw))
  (js/console.log "START"))

(defn stop []
  (js.clear)
  (js/console.log "STOP"))

(defn ^:export init []
  (js/console.log "INIT")
  (start))
