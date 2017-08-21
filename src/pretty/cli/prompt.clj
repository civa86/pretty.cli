(ns pretty.cli.prompt
  (:require [pretty.cli.colors :as c]
            [pretty.cli.ansi-escapes :as esc])
  (:import [jline Terminal]))

;Private functions

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
                 (let [opt-label (if (string? opt) opt (get opt :label))]
                   (if (= i selected)
                     (c/cyan (str " ▸  " opt-label))
                     (str "    " opt-label)))) opts))

(defn- get-check-list-options
  "Return the options list, ready to be printed"
  [opts selected]
  (map-indexed (fn [i opt]
                 (let [opt-label (if (string? opt) opt (get opt :label))]
                   (if (= i selected)
                     (c/cyan (str " ▸  " opt-label))
                     (str "    " opt-label)))) opts))

(defn- prompt-select-list
  "Prompt a list of elements with 1 selected answer" ;TODO write right desc...
  [question choices selected submitted? first-rendering?]
  (let [term (Terminal/getTerminal)]
    (if (= true submitted?)
      (let [opt-selected (nth choices selected nil)
            opt-selected-label (if (string? opt-selected) opt-selected (get opt-selected :label))
            opt-selected-val (if (string? opt-selected) opt-selected (get opt-selected :value))]
        (.restoreTerminal term)
        (println (str "[" (c/green "▸") "] " question ": " (c/cyan opt-selected-label)))
        opt-selected-val)

      (let [num-opts (count choices)
            tip (if (= true first-rendering?) (c/dim " (UP/DOWN move, ENTER submit)") "")]
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
                                                 (prompt-select-list question choices (mod (inc selected) num-opts) false false))))))
            :else (do
                    (clear-list! num-opts)
                    (prompt-select-list question choices selected false false))))))))

(defn- prompt-check-list
  "TODO write right desc..."
  [question choices selected submitted? first-rendering?]
  (clojure.pprint/pprint choices)
  ;(let [term (Terminal/getTerminal)]
  ;  (if (= true submitted?)
  ;    (println "asd")
  ;    ;(let [opt-selected (nth choices selected nil)
  ;    ;      opt-selected-label (if (string? opt-selected) opt-selected (get opt-selected :label))
  ;    ;      opt-selected-val (if (string? opt-selected) opt-selected (get opt-selected :value))]
  ;    ;  (.restoreTerminal term)
  ;    ;  (println (str "[" (c/green "▸") "] " question ": " (c/cyan opt-selected-label)))
  ;    ;  opt-selected-val)
  ;
  ;    (let [num-opts (count choices)
  ;          tip (if (= true first-rendering?) (c/dim " (UP/DOWN move, ENTER submit)") "")]
  ;      (println (str "[" (c/green "?") "] " question ":" tip))
  ;      (doseq [opt (get-check-list-options choices selected)]
  ;        (println opt))
  ;
  ;      ;(let [pressed (.readCharacter term System/in)]
  ;      ;  (cond
  ;      ;    (= 10 pressed) (do
  ;      ;                     (clear-list! num-opts)
  ;      ;                     (prompt-select-list question choices selected true false))
  ;      ;
  ;      ;    (= 27 pressed) (let [next (.readCharacter term System/in)]
  ;      ;                     (if (= 91 next)
  ;      ;                       (let [last (.readCharacter term System/in)]
  ;      ;                         (cond
  ;      ;                           (= 65 last) (do
  ;      ;                                         (clear-list! num-opts)
  ;      ;                                         (prompt-select-list question choices (mod (dec selected) num-opts) false false))
  ;      ;                           (= 66 last) (do
  ;      ;                                         (clear-list! num-opts)
  ;      ;                                         (prompt-select-list question choices (mod (inc selected) num-opts) false false))))))
  ;      ;    :else (do
  ;      ;            (clear-list! num-opts)
  ;      ;            (prompt-select-list question choices selected false false))))
  ;      ;
  ;      )))

  )

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

(defn select-list
  "Prompt a list of elements with 1 selected answer"
  [question choices]
  (prompt-select-list question choices 0 false true))

(defn check-list
  "TODO"
  [question choices]
  (as-> choices ch
       (map #(if (string? %) (-> {}
                                 (assoc :label %)
                                 (assoc :value %)
                                 (assoc :checked false))) ch)
        (prompt-check-list question ch 0 false true)))
