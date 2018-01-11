(ns app.main
  (:require [app.walker :as walker]
            [app.distribution :as dist]))

(def width 640)
(def height 600)

(defonce state
  (atom {:walker
         {:x (/ width 2)
          :y (/ height 2)
          :xoff 0
          :yoff 10000}}))

(defn setup []
  (js/createCanvas width height))

(defn draw []
  ;(dist/draw-color-splatter)
  (swap! state assoc :walker (walker/walker-noise-step (:walker @state)))
  (walker/walker-draw-circle (:walker @state)))


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
