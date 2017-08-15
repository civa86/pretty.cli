(ns pretty.terminal.screen)

(def esc-code "\033[")

;TODO mapping escapes
;....http://ascii-table.com/ansi-escape-sequences.php
(defn get-escape-code
  "Get ecape code given quantity and operation identifier"
  ([op quantity]
    (if (> quantity 0)
      (str esc-code quantity op)
      (str esc-code op)))
  ([op] (get-escape-code op 0)))

(defn cursor-save! []
  "Save cursor position"
  (print (get-escape-code "s")))

(defn cursor-restore! []
  "Save cursor position"
  (println (get-escape-code "u")))

(defn cursor-up!
  "Move cursor up in the shell"
  ([num-lines] (print (get-escape-code "A" num-lines)))
  ([] (cursor-up! 2)))

(defn delete-line!
  "Delete k lines"
  ([k] (->> (range 0 k)
            (map (fn [x] (get-escape-code "K")))
            (clojure.string/join "\n")
            (println)))
  ([] (delete-line! 1)))

(defn clear! []
  "Clear shell screen"
  (println (get-escape-code "J" 2)))
