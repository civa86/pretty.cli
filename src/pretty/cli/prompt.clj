(ns pretty.cli.prompt
  (:require [pretty.cli.colors :as c]
            [pretty.cli.ansi-escapes :as esc]
            [schema.core :as s]
            [clojure.string :as str])
  (:import [jline Terminal]))
;TODO fn docs
(s/defschema ^:private CHOICE {(s/required-key :label)   (s/constrained s/Str #(not (clojure.string/blank? %)))
                               (s/required-key :value)   (s/constrained s/Str #(not (clojure.string/blank? %)))
                               (s/optional-key :checked) s/Bool})

;Private functions

(defn- validate-choices!
  "Validate the choices set"
  [choices]
  (doseq [c choices]
    (s/validate CHOICE c)))

(defn- clear-list!
  "Clear the current printed list"
  [num-opts]
  (let [n (+ 2 num-opts)]
    (println (esc/cursor-up n))
    (doseq [x (range 1 n)]
      (println (esc/erase-line)))
    (println (esc/cursor-up n))))

(defn- print-submitted-input!
  "Print the submitted answer message"
  [msg]
  (println (esc/cursor-up 2))
  (print (esc/erase-line))
  (println msg)
  (flush))

(defn- get-list-options
  "Return the options list, ready to be printed"
  [opts selected]
  (map-indexed (fn [i opt]
                 (let [opt-label (get opt :label)]
                   (if (= i selected)
                     (c/cyan (str " ▸  " opt-label))
                     (str "    " opt-label)))) opts))

(defn- get-check-list-options
  "Return the options list, ready to be printed"
  [opts selected]
  (map-indexed (fn [i opt]
                 (let [opt-label (get opt :label)
                       opt-checked-label (if (= true (get opt :checked))
                                           (str (c/yellow "◉  ") opt-label)
                                           (str "◯  " opt-label))]
                   (if (= i selected)
                     (str (c/cyan " ▸  ") opt-checked-label)
                     (str "    " opt-checked-label)))) opts))

(defn- restore-terminal!
  "Restore Terminal configuration"
  [term]
  (.restoreTerminal term)
  (print (esc/cursor-show)))

(defn- prompt-select-list
  "Prompt a list of elements with 1 selected answer"        ;TODO write right desc...
  [question choices selected submitted? first-rendering?]
  (validate-choices! choices)
  (let [term (Terminal/getTerminal)]
    (print (esc/cursor-hide))
    (if (= true submitted?)
      (let [opt-selected (nth choices selected nil)
            opt-selected-label (get opt-selected :label)
            opt-selected-val (get opt-selected :value)]
        (restore-terminal! term)
        (println (str "[" (c/green "▸") "] " question ": " (c/cyan opt-selected-label)))
        opt-selected-val)

      (let [num-opts (count choices)
            tip (if (= true first-rendering?) (c/txt-dim " (UP/DOWN move, ENTER submit)") "")]
        (println (str "[" (c/green "?") "] " question ":" tip))
        (doseq [opt (get-list-options choices selected)]
          (println opt))

        (let [pressed (.readCharacter term System/in)]
          (cond
            (= 10 pressed) (do
                             (clear-list! num-opts)
                             (prompt-select-list question choices selected true false))

            (= 27 pressed) (let [next (.readCharacter term System/in)]
                             (if (= 91 next)
                               (let [last (.readCharacter term System/in)]
                                 (cond
                                   (= 65 last) (do
                                                 (clear-list! num-opts)
                                                 (prompt-select-list question choices (mod (dec selected) num-opts) false false))
                                   (= 66 last) (do
                                                 (clear-list! num-opts)
                                                 (prompt-select-list question choices (mod (inc selected) num-opts) false false))))
                               (restore-terminal! term)))
            :else (do
                    (clear-list! num-opts)
                    (prompt-select-list question choices selected false false))))))))

(defn- prompt-check-list
  "TODO write right desc..."
  [question choices selected submitted? first-rendering?]
  (validate-choices! choices)
  (let [term (Terminal/getTerminal)]
    (print (esc/cursor-hide))
    (if (= true submitted?)
      (let [selected-choices (->> choices
                                  (filter #(= true (get % :checked)))
                                  (map #(get % :value)))]
        (.restoreTerminal term)
        (print (esc/cursor-show))
        (println (str "[" (c/green "▸") "] " question ": " (c/cyan (if (> (count selected-choices) 0)
                                                                     (str/join ", " selected-choices)
                                                                     "-"))))
        selected-choices)

      (let [num-opts (count choices)
            tip (if (= true first-rendering?) (c/txt-dim " (UP/DOWN move, SPACE check/uncheck, ENTER submit)") "")]
        (println (str "[" (c/green "?") "] " question ":" tip))
        (doseq [opt (get-check-list-options choices selected)]
          (println opt))

        (let [pressed (.readCharacter term System/in)]
          (cond
            (= 10 pressed) (do
                             (clear-list! num-opts)
                             (prompt-check-list question choices selected true false))

            (= 32 pressed) (let [new-choices (map-indexed (fn [i opt]
                                                            (if (= i selected)
                                                              (assoc opt :checked (not (get opt :checked false)))
                                                              opt)) choices)]
                             (clear-list! num-opts)
                             (prompt-check-list question new-choices selected false false))

            (= 27 pressed) (let [next (.readCharacter term System/in)]
                             (if (= 91 next)
                               (let [last (.readCharacter term System/in)]
                                 (cond
                                   (= 65 last) (do
                                                 (clear-list! num-opts)
                                                 (prompt-check-list question choices (mod (dec selected) num-opts) false false))
                                   (= 66 last) (do
                                                 (clear-list! num-opts)
                                                 (prompt-check-list question choices (mod (inc selected) num-opts) false false))))
                               (restore-terminal! term)))
            :else (do
                    (clear-list! num-opts)
                    (prompt-check-list question choices selected false false))))))))

; Public functions

(defn input
  "Prompt question with free input answer"
  ([question validate-fn]
   (print (str "[" (c/green "?") "] " question ": "))
   (flush)
   (let [answer (read-line)
         validation-error (if (nil? validate-fn) nil (validate-fn answer))]
     (if (nil? validation-error)
       (do
         (print-submitted-input! (str "[" (c/green "▸") "] " question ": " (c/cyan answer)))
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
           (print-submitted-input! (str "[" (c/green "▸") "] " question "? " (c/cyan "Yes")))
           true)

         (or (and (= "" answer) (= false default-value)) (not (nil? (re-matches #"n|N|no" answer))))
         (do
           (print-submitted-input! (str "[" (c/green "▸") "] " question "? " (c/cyan "No")))
           false)

         :else (do
                 (println (str "[" (c/red "!") "] " (c/red "Please answer 'yes' or 'no'")))
                 (confirm question default-value))))))
  ([question] (confirm question true)))

(defn list-select
  "Prompt a list of elements with 1 selected answer"
  [question choices]
  (as-> choices ch
        (map #(if (string? %) (-> {}
                                  (assoc :label %)
                                  (assoc :value %))
                              %
                              ) ch)
        (prompt-select-list question ch 0 false true)))

(defn list-checkbox
  "TODO"
  [question choices]
  (as-> choices ch
        (map #(if (string? %) (-> {}
                                  (assoc :label %)
                                  (assoc :value %)
                                  (assoc :checked false))
                              %
                              ) ch)
        (prompt-check-list question ch 0 false true)))
