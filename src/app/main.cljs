(ns app.main
  (:require [app.walker :as walker]
            [app.distribution :as dist]
            [app.noise :as noise]
            [app.vector :as vector]
            [app.mover :as mover]))

(def width 600)
(def height 400)

(defn accelerate []
  (let [x (:x (:acceleration (:mover @state)))
        new-x (+ x 0.01)]
    (swap! state assoc-in [:mover :acceleration] {:x new-x})))

(defn decelerate []
  (let [x (:x (:acceleration (:mover @state)))
        new-x (- x 0.01)]
    (swap! state assoc-in [:mover :acceleration] {:x new-x})))

(defonce state
  (atom {:mover (mover/create 100 200 0 0 0.001 0.001 2)
         :xoff 2
         :yoff 100057}))

(defn setup []
  (js/createCanvas width height))

(defn draw []
  (let [xoff (:xoff @state)
        yoff (:yoff @state)
        updated-mover (mover/updates-perlin-acceleration (:mover @state) width height xoff yoff)]
    (swap! state assoc :mover updated-mover) ; emulates update()
    (swap! state update :xoff inc :yoff inc)
    ;(js/console.log xoff)
    (vector/draw (:location (:mover @state)))))

(defn keypressed []
  (let [left 37
        right 39
        up 38
        down 40]
    (condp = js/keyCode
      up (accelerate)
      down (decelerate)
      (js/console.log "not configured"))))

;; start stop pattern as described in
;; https://github.com/thheller/shadow-cljs/wiki/ClojureScript-for-the-browser
(defn start []
  (doto js/window
    (aset "setup" setup)
    (aset "draw" draw)
    (aset "keyPressed" keypressed))
  (js/console.log "START"))

(defn stop []
  (js/clear)
  (js/console.log "STOP"))

(defn ^:export init []
  (js/console.log "INIT")
  (start))
