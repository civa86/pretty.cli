(ns pretty.cli.colors
  (:require [pretty.cli.ansi-escapes :as esc]))

(def ^:private fg-const 30)
(def ^:private bg-const 40)

(defn- build-color-code
  "Build color code string"
  ([color-val txt is-background?]
   (let [c (if (= true is-background?) bg-const fg-const)]
     (str esc/esc-code (+ c color-val) "m" txt esc/reset-code)))
  ([color-val txt] (build-color-code color-val txt false)))

(defn txt-bold "Return `txt` string with bold display attribute" [txt] (str esc/esc-code "1m" txt esc/reset-code))
(defn txt-dim "Return `txt` string with dim display attribute" [txt] (str esc/esc-code "2m" txt esc/reset-code))
(defn txt-underscore "Return `txt` string with underscore display attribute" [txt] (str esc/esc-code "4m" txt esc/reset-code))
(defn txt-blink "Return `txt` string  with blink display attribute" [txt] (str esc/esc-code "5m" txt esc/reset-code))

(defn black "Return `txt` string with black color" [txt] (build-color-code 0 txt))
(defn red "Return `txt` string with red color" [txt] (build-color-code 1 txt))
(defn green "Return `txt` string with green color" [txt] (build-color-code 2 txt))
(defn yellow "Return `txt` string with yellow color" [txt] (build-color-code 3 txt))
(defn blue "Return `txt` string with blue color" [txt] (build-color-code 4 txt))
(defn magenta "Return `txt` string with magenta color" [txt] (build-color-code 5 txt))
(defn cyan "Return `txt` string with cyan color" [txt] (build-color-code 6 txt))
(defn white "Return `txt` string with white color" [txt] (build-color-code 7 txt))

(defn black-bg "Return `txt` string with black background color" [txt] (build-color-code 0 txt true))
(defn red-bg "Return `txt` string with red background color" [txt] (build-color-code 1 txt true))
(defn green-bg "Return `txt` string with green background color" [txt] (build-color-code 2 txt true))
(defn yellow-bg "Return `txt` string with yellow background color" [txt] (build-color-code 3 txt true))
(defn blue-bg "Return `txt` string with blue background color" [txt] (build-color-code 4 txt true))
(defn magenta-bg "Return `txt` string with magenta background color" [txt] (build-color-code 5 txt true))
(defn cyan-bg "Return `txt` string with cyan background color" [txt] (build-color-code 6 txt true))
(defn white-bg "Return `txt` string with white background color" [txt] (build-color-code 7 txt true))
