(ns app.distribution)

(defn draw-gaussian-line
  "showcase normal distribution"
  []
  (let [sd 60
        mean 320
        x (js/randomGaussian mean sd)]
    (js/noStroke)
    (js/fill 0 0 0 10)
    (js/ellipse x 180 16 16)))

(defn draw-color-splatter
  "also showcase normal distribution"
  []
  (let [sd 60
        mean 320
        x (js/randomGaussian mean sd)
        y (js/randomGaussian mean sd)
        r (js/randomGaussian 20 6)
        c (js/randomGaussian 180 50)]
    (js/noStroke)
    (js/fill 130 c 30)
    (js/ellipse x y r r)))
