(ns app.main
  (:require [app.walker :as walker]
            [app.distribution :as dist]
            [app.noise :as noise]
            [app.vector :as vector]
            [app.mover :as mover]))

(def width 600)
(def height 400)

(defonce state
  (atom {:movers []}))

(defn setup []
  (js/createCanvas width height)
  (swap! state assoc :movers (mover/seed 5)))

(defn draw []
  (let [list (:movers @state)]
;    (swap! state assoc :movers (mapv mover/updates list))
    (dorun
     (for [m list]
       (let [location (:location m)]
         (js/ellipse (:x location) (:y location) 20 20))))
    ))

;; (defn keypressed []
;;   (let [left 37
;;         right 39
;;         up 38
;;         down 40]
;;     (condp = js/keyCode
;;       up (mover/accelerate state)
;;       down (mover/decelerate)
;;       (js/console.log "not configured"))))

;; start stop pattern as described in
;; https://github.com/thheller/shadow-cljs/wiki/ClojureScript-for-the-browser
(defn start []
  (doto js/window
    (aset "setup" setup)
    ;(aset "keyPressed" keypressed)
    (aset "draw" draw))
  (js/console.log "START"))

(defn stop []
  (js/clear)
  (js/console.log "STOP"))

(defn ^:export init []
  (js/console.log "INIT")
  (start))
