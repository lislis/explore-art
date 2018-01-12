(ns app.main
  (:require [app.walker :as walker]
            [app.distribution :as dist]
            [app.noise :as noise]))

(def width 600)
(def height 400)

(defonce state
  (atom {:xoff 0.0 :yoff 10000.0}))

(defn setup []
  (js/createCanvas width height))

(defn draw []
  )

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
