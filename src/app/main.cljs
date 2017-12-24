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

(defn walker-prob [walker]
  (let [prob (js/random 1)]
    ;(js/console.log prob)
    (cond
      (< prob 0.4) (update-in walker [:x] inc)
      (< prob 0.6) (update-in walker [:x] dec)
      (< prob 0.8) (update-in walker [:y] inc)
      :else (update-in walker [:y] dec))))

;; not used here
(defn walker-step [walker]
  (let [step-x (js/random -1 1)
        step-y (js/random -1 1)]
    {:x (+ step-x (:x walker))
     :y (+ step-y (:y walker))}))

(defn setup []
  (js/createCanvas width height))

(defn draw []
  ;(js/clear)
  (swap! state assoc :walker (walker-prob (:walker @state)))
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
