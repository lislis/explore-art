(ns app.walker)

;; Define an atom with the walker state.
;; Choose either walker-step, walker-prob-right or walker-mouse
;; to define the walker behavior.
;; Then draw the walker.
;; Example below
;;
;; (defonce state
;;   (atom {:walker
;;         {:x (/ width 2) :y (/ height 2)}}))
;;
;; (defn setup []
;;   (js/createCanvas width height))
;;
;; (defn draw []
;;   (swap! state assoc :walker (walker/walker-mouse (:walker @state)))
;;   (walker/walker-draw (:walker @state)))
;;

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

(defn walker-prob-right
  "random walker with preference to right"
  [walker]
  (let [prob (js/random 1)]
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

(defn walker-gaussian
  "random walker with Gaussian step size"
  [walker]
  (let [direction (js/Math.floor (js/random 0 4))
        size (js/randomGaussian)]
    (cond
      (= direction 0) {:x (+ size (:x walker)) :y (:y walker)}
      (= direction 1) {:x (- size (:x walker)) :y (:y walker)}
      (= direction 2) {:x (:x walker) :y (+ size (:y walker))}
      :else {:x (:x walker) :y (- size (:y walker))})))
