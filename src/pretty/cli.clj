(ns pretty.cli
  (:require [clojure.pprint]
            [pretty.cli.ansi-escapes :as esc]
            [pretty.cli.figlet :as flf]
            [pretty.cli.prompt :as prompt])
  (:import jline.Terminal))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (esc/clear-screen))
  (println (flf/figlet "TEST"))
  (let [
        first-name (prompt/input "First name")
        gender (prompt/select-list "Gender" [{:label "Male" :value "M"} {:label "Female" :value "F"}])
        last-name (prompt/input "Last name")
        ;confirm? (prompt/confirm "Are you sure")
        ]


    ;(if (= true confirm?)
    ;  (println "\nThank you," first-name last-name "\n")
    ;  (-main))

    ))
