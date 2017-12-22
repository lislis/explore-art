(ns app.main)

(defn setup []
  (js/createCanvas 800 600))

(defn draw []
  ;(js/clear)
  (js/fill (if js/mouseIsPressed 0 255))
  (js/ellipse js/mouseX js/mouseY 80 80))


;; start stop pattern as described in
;; https://github.com/thheller/shadow-cljs/wiki/ClojureScript-for-the-browser

(defn start []
  ;; do whatever you need to start your app
  (doto js/window
    (aset "setup" setup)
    (aset "draw" draw))
  (js/console.log "START"))

(defn stop []
  ;; shutdown whatever start did but not init
  (js/console.log "STOP"))

(defn ^:export init []
  ;; put code here that will only ever be called ONCE
  (js/console.log "INIT")
  ;; then call start
  (start))
