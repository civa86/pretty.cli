(ns pretty.cli.colors
  (:require [pretty.cli.ansi-escapes :as esc]))
;TODO fn docs
(def ^:private fg-const 30)
(def ^:private bg-const 40)

(defn- build-color-code
  "Build color code string"
  ([color-val txt is-background?]
   (let [c (if (= true is-background?) bg-const fg-const)]
     (str esc/esc-code (+ c color-val) "m" txt esc/reset-code)))
  ([color-val txt] (build-color-code color-val txt false)))

(defn txt-bold "Return txt with bold display attribute" [txt] (str esc/esc-code "1m" txt esc/reset-code))
(defn txt-dim "Return txt with dim display attribute" [txt] (str esc/esc-code "2m" txt esc/reset-code))
(defn txt-underscore "Return txt with underscore display attribute" [txt] (str esc/esc-code "4m" txt esc/reset-code))
(defn txt-blink "Return txt with blink display attribute" [txt] (str esc/esc-code "5m" txt esc/reset-code))

(defn black "Text color black" [txt] (build-color-code 0 txt))
(defn red "Text color red" [txt] (build-color-code 1 txt))
(defn green "Text color green" [txt] (build-color-code 2 txt))
(defn yellow "Text color yellow" [txt] (build-color-code 3 txt))
(defn blue "Text color blue" [txt] (build-color-code 4 txt))
(defn magenta "Text color magenta" [txt] (build-color-code 5 txt))
(defn cyan "Text color cyan" [txt] (build-color-code 6 txt))
(defn white "Text color white" [txt] (build-color-code 7 txt))

(defn black-bg "Text background color black" [txt] (build-color-code 0 txt true))
(defn red-bg "Text background color red" [txt] (build-color-code 1 txt true))
(defn green-bg "Text background color green" [txt] (build-color-code 2 txt true))
(defn yellow-bg "Text background color yellow" [txt] (build-color-code 3 txt true))
(defn blue-bg "Text background color blue" [txt] (build-color-code 4 txt true))
(defn magenta-bg "Text background color magenta" [txt] (build-color-code 5 txt true))
(defn cyan-bg "Text background color cyan" [txt] (build-color-code 6 txt true))
(defn white-bg "Text background color white" [txt] (build-color-code 7 txt true))
