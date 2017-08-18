(ns pretty.cli.prompt
  (:require [pretty.cli.colors :as c]
            [pretty.cli.ansi-escapes :as esc]))

(defn- print-submitted
  "Print the submitted answer message"
  [msg]
  (println (esc/cursor-up 2))
  (print (esc/erase-line))
  (println msg)
  (flush))

(defn input
  "Prompt question with free input answer"
  ([question validate-fn]
   (print (str "[" (c/green "?") "] " question ": "))
   (flush)
   (let [answer (read-line)
         validation-error (if (nil? validate-fn) nil (validate-fn answer))]
     (if (nil? validation-error)
       (do
         (print-submitted (str "[" (c/green "▸") "] " question ": " (c/cyan answer)))
         answer)
       (do
         (println (str "[" (c/red "!") "] " (c/red validation-error)))
         (input question validate-fn)))))
  ([question] (input question nil)))

(defn confirm
  "Prompt confirmation with y/n answer"
  ([question default-value]
   (let [opt-yes (if (= true default-value) "Y" "y")
         opt-no (if (= false default-value) "N" "n")]
     (print (str "[" (c/green "?") "] " question "? [" opt-yes "/" opt-no "] "))
     (flush)
     (let [answer (read-line)]
       (cond
         (or (and (= "" answer) (= true default-value)) (not (nil? (re-matches #"y|Y|yes" answer))))
         (do
           (print-submitted (str "[" (c/green "▸") "] " question "? " (c/cyan "Yes")))
           true)

         (or (and (= "" answer) (= false default-value)) (not (nil? (re-matches #"n|N|no" answer))))
         (do
           (print-submitted (str "[" (c/green "▸") "] " question "? " (c/cyan "No")))
           false)

         :else (do
                 (println (str "[" (c/red "!") "] " (c/red "Please answer 'yes' or 'no'")))
                 (confirm question default-value))))))
  ([question] (confirm question true)))
