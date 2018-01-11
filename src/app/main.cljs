(ns app.main
  (:require [app.walker :as walker]
            [app.distribution :as dist]))

(def width 640)
(def height 600)

(defonce state
  (atom {:xoff 0.0}))

(defn traverse-pixels [width height]
  (dorun
   (let [xoff 0
         yoff 10000]
     (for [y (range height)
           x (range width)]
       (js/set x y (js/random 255))))))

(defn setup []
  (js/createCanvas width height))

(defn draw []
  (js/loadPixels)
  (traverse-pixels width height)
  (js/updatePixels))

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
