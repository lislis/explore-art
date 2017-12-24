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

(defn mouse-offset
  "calculate in which direction to update"
  [walker]
  (let [x (:x walker)
        y (:y walker)]
    (-> walker
        (assoc-in [:x] (cond
                         (< x js/mouseX) (inc x)
                         (> x js/mouseX) (dec x)
                         :else x))
        (assoc-in [:y] (cond
                         (< y js/mouseY) (inc y)
                         (> y js/mouseY) (dec y)
                         :else y)))))

(defn walker-mouse
  "random walker with preference to mouse position"
  [walker]
  (let [prob (js/random 1)]
    (cond
      (< prob 0.5) (mouse-offset walker)
      :else (walker-step walker))))

(defn walker-prob
  "random walker with preference to right"
  [walker]
  (let [prob (js/random 1)]
    ;(js/console.log prob)
    (cond
      (< prob 0.4) (update-in walker [:x] inc)
      (< prob 0.6) (update-in walker [:x] dec)
      (< prob 0.8) (update-in walker [:y] inc)
      :else (update-in walker [:y] dec))))

(defn walker-step
  "random walker"
  [walker]
  (let [step-x (js/random -1 1)
        step-y (js/random -1 1)]
    {:x (+ step-x (:x walker))
     :y (+ step-y (:y walker))}))

(defn setup []
  (js/createCanvas width height))

(defn draw []
  ;(js/clear)
  (swap! state assoc :walker (walker-mouse (:walker @state)))
  (walker-draw (:walker @state)))


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
