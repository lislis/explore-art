(ns app.vector)

;; some vector helpers
;; state should look something like this
;; (defonce state
;;   (atom {:location (create 100 100)
;;          :velocity (create 2.5 3)}))

;; draw of bounce ball example
;; (defn draw []
;;   (let [v (add (:location @state) (:velocity @state))]
;;     (swap! state assoc :location v)
;;     (bounce-velocity state width height)
;;     (draw (:location @state))))


(defn create
  "creates a vector"
  [x y]
  {:x x :y y})

(defn add
  "adds two vectors together"
  [vec1 vec2]
  (let [x (+ (:x vec1) (:x vec2))
        y (+ (:y vec1) (:y vec2))]
    (create x y)))

(defn bounce-velocity [state width height]
  (if (or (> (:x (:location @state)) width) (< (:x (:location @state)) 0))
    (swap! state assoc :velocity {:x (* -1 (:x (:velocity @state))) :y (:y (:velocity @state))} ))

  (if (or (> (:y (:location @state)) height) (< (:y (:location @state)) 0))
    (swap! state assoc :velocity {:y (* -1 (:y (:velocity @state))) :x (:x (:velocity @state))} )))

(defn draw [vector]
  (js/background 180 220 140)
  (js/stroke 0)
  (js/fill 200 50 100)
  (js/ellipse (:x vector) (:y vector) 20 20))
