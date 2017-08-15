(ns pretty.terminal.screen)

(def esc-code "\033[")

(defn get-escape-code
  "Get ecape code given quantity and operation identifier"
  ([op quantity]
    (if (> quantity 0)
      (str esc-code quantity op)
      (str esc-code op)))
  ([op] (get-escape-code op 0)))

(defn cursor-up!
  "Move cursor up in the shell"
  ([num-lines] (println (get-escape-code "A" num-lines)))
  ([] (cursor-up! 2)))

(defn clear! []
  "clear shell screen"
  (println (get-escape-code "J" 2)))
