(ns app.main
  (:require [app.walker :as walker]
            [app.distribution :as dist]
            [app.noise :as noise]
            [app.vector :as vector]))

(def width 600)
(def height 400)

(defonce state
  (atom {:location (vector/create 100 100)
         :velocity (vector/create 2.5 3)}))

(defn setup []
  (js/createCanvas width height))

(defn draw []
  (let [v (vector/add (:location @state) (:velocity @state))
        m (vector/create js/mouseX js/mouseY)
        c (vector/create (/ width 2) (/ height 2))
        mouse (vector/sub m c)]
    ;(swap! state assoc :location v)
    ;(vector/bounce-velocity state width height)
                                        ;(vector/draw (:location @state))
    (js/translate (/ width 2) (/ height 2))
    (js/line 0 0 (:x mouse) (:y mouse))))

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
