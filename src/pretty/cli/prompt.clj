(ns pretty.cli.prompt
  (:require [pretty.cli.colors :as c]
            [pretty.cli.ansi-escapes :as esc]))

(defn input
  "Prompt question with free input answer"
  ([question validate-fn]
   (print (str "[" (c/green "?") "] " question ": "))
   (flush)
   (let [answer (read-line)
         is-valid? (if (nil? validate-fn) true (validate-fn answer))]
     (if (= true is-valid?)
       (do
         (println (esc/cursor-up 2))
         (print (esc/erase-line))
         (println (str "[" (c/green "▸") "] " question ": " (c/cyan answer)))
         answer))))
  ([question] (input question nil)))
