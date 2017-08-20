(ns pretty.cli.prompt
  (:require [pretty.cli.colors :as c]
            [pretty.cli.ansi-escapes :as esc])
  (:import jline.Terminal))

(def choosing? (atom true))

(defn- print-submitted!
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
         (print-submitted! (str "[" (c/green "▸") "] " question ": " (c/cyan answer)))
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
           (print-submitted! (str "[" (c/green "▸") "] " question "? " (c/cyan "Yes")))
           true)

         (or (and (= "" answer) (= false default-value)) (not (nil? (re-matches #"n|N|no" answer))))
         (do
           (print-submitted! (str "[" (c/green "▸") "] " question "? " (c/cyan "No")))
           false)

         :else (do
                 (println (str "[" (c/red "!") "] " (c/red "Please answer 'yes' or 'no'")))
                 (confirm question default-value))))))
  ([question] (confirm question true)))

(defn- get-options
  "Return the options list, ready to be printed"
  [opts selected]
  (map-indexed (fn [i opt]
                 (let [opt-label (if (string? opt) opt (get opt :label))]
                   (if (= i selected)
                     (c/cyan (str " ▸  " opt-label))
                     (str "    " opt-label)))) opts))

(defn- prompt-select-list
  ;TODO write right desc...
  "Prompt a list of elements with 1 selected answer"
  [question choices selected clear?]

  (if (= true clear?)
    (let [n (+ 2 (count choices))]
      (println (esc/cursor-up n))
      (doseq [x (range 1 n)]
        (println (esc/erase-line))
        )
      (println (esc/cursor-up n))
      ))

  (println (str "[" (c/green "?") "] " question ":" selected))
  (doseq [opt (get-options choices selected)]
    (println opt))
  (let [term (Terminal/getTerminal)]
    (if @choosing?
      (let [pressed (.readCharacter term System/in)]
        (cond
          (= 10 pressed) (do
                           (reset! choosing? false)
                           (prompt-select-list question choices selected true)
                           )
          (= 27 pressed) (let [next (.readCharacter term System/in)]
                           (if (= 91 next)
                             (let [last (.readCharacter term System/in)]
                               (cond
                                 (= 65 last) (prompt-select-list question choices (mod (dec selected) (count choices)) true)
                                 (= 66 last) (prompt-select-list question choices (mod (inc selected) (count choices)) true)
                                 ))))))
      selected
      )
    ))

(defn select-list
  "Prompt a list of elements with 1 selected answer"
  [question choices]
  (do
    (reset! choosing? true)
    (prompt-select-list question choices 0 false)))
