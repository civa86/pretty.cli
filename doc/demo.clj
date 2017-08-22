(ns demo
  (:require [pretty.cli.ansi-escapes :as esc]
            [pretty.cli.colors :as c]
            [pretty.cli.figlet :as flf]
            [pretty.cli.prompt :as prompt]))

(defn -main
  "Demo usage of pretty.cli utilities"
  []
  (println (esc/clear-screen))
  (println (c/cyan (flf/figlet "Welcome")))
  (let [first-name (prompt/input "First name" (fn [x] (if (= "" x) "Sorry?")))
        last-name (prompt/input "Last name" (fn [x] (if (= "" x) "Sorry?")))
        gender (prompt/list-select "Gender" [{:label "Male" :value "M"} {:label "Female" :value "F"}])
        confirm? (prompt/confirm "Are you sure")]

    (if (= true confirm?)
      (do
        (println (esc/clear-screen))
        (println (c/black-bg (c/cyan (flf/figlet (str "Hi, " (if (= "M" gender) "Mr." "Ms.")) "starwars"))))
        (println (c/black-bg (c/magenta (flf/figlet first-name))))
        (println (c/black-bg (c/yellow (flf/figlet last-name)))))
      (-main))))
