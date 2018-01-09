(ns app.main
  (:require [app.walker :as walker]))

(def width 800)
(def height 600)

(defonce state
  (atom {:walker
         {:x (/ width 2) :y (/ height 2)}}))

(defn setup []
  (js/createCanvas width height))

(defn draw []
  ;(js/clear)
  (swap! state assoc :walker (walker/walker-step (:walker @state)))
  (walker/walker-draw (:walker @state)))


;; start stop pattern as described in
;; https://github.com/thheller/shadow-cljs/wiki/ClojureScript-for-the-browser
(defn start []
  (doto js/window
    (aset "setup" setup)
    (aset "draw" draw))
  (js/console.log "START"))

(defn stop []
  (js/console.log "STOP"))

(defn ^:export init []
  (js/console.log "INIT")
  (start))
