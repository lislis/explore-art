(ns app.main)

(def width 800)
(def height 600)

(defonce state
  (atom {:walker
         {:x (/ width 2) :y (/ height 2)}}))

(defn walker-draw [walker]
  (let [x (:x walker)
        y (:y walker)]
    (js/point x y)))

(defn walker-step [walker]
  (let [step-x (js/random -1 1)
        step-y (js/random -1 1)]
    {:x (+ step-x (:x walker))
     :y (+ step-y (:y walker))}))

(defn setup []
  (js/createCanvas width height))

(defn draw []
  ;(js/clear)
  (swap! state assoc :walker (walker-step (:walker @state)))
  (walker-draw (:walker @state)))


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
