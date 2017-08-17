(ns pretty.cli.figlet
  (:require [clojure.java.io :as io])
  (:import [com.github.lalyos.jfiglet FigletFont]))

(defn figlet
  "Return 'text' in ascii art with the selected 'font' FIGfonts"
  ([text font]
    ;TODO manage if font not present....
   (-> (str "resources/fonts/" font ".flf")
       (io/input-stream)
       (FigletFont/convertOneLine text)))
  ([text] (figlet text "standard")))
