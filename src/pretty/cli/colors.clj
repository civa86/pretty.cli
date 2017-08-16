(ns pretty.cli.colors)

(def reset-color-code "\033[0m")
(def fg-const 30)
(def bg-const 40)

(defn- build-color-code
  "Build color code string"
  ([color-val txt is-background?]
   (let [c (if (= true is-background?) bg-const fg-const)]
     (str "\033[" (+ c color-val) "m" txt reset-color-code)))
  ([color-val txt] (build-color-code color-val txt false)))

(defn black [txt] (build-color-code 0 txt))
(defn red [txt] (build-color-code 1 txt))
(defn green [txt] (build-color-code 2 txt))
(defn yellow [txt] (build-color-code 3 txt))
(defn blue [txt] (build-color-code 4 txt))
(defn magenta [txt] (build-color-code 5 txt))
(defn cyan [txt] (build-color-code 6 txt))
(defn white [txt] (build-color-code 7 txt))

(defn black-bg [txt] (build-color-code 0 txt true))
(defn red-bg [txt] (build-color-code 1 txt true))
(defn green-bg [txt] (build-color-code 2 txt true))
(defn yellow-bg [txt] (build-color-code 3 txt true))
(defn blue-bg [txt] (build-color-code 4 txt true))
(defn magenta-bg [txt] (build-color-code 5 txt true))
(defn cyan-bg [txt] (build-color-code 6 txt true))
(defn white-bg [txt] (build-color-code 7 txt true))
