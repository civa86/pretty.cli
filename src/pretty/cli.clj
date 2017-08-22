(ns pretty.cli
  (:require [clojure.pprint]
            [pretty.cli.ansi-escapes :as esc]
            [pretty.cli.figlet :as flf]
            [pretty.cli.prompt :as prompt]))



(defn meeting
  "Meet my user"
  []
  (println (esc/clear-screen))
  (println (flf/figlet "Welcome"))
  (let [first-name (prompt/input "First name" (fn [x] (if (= "" x) "Sorry?")))
        last-name (prompt/input "Last name" (fn [x] (if (= "" x) "Sorry?")))
        gender (prompt/select-list "Gender" [{:label "Male" :value "M"} {:label "Female" :value "F"}])
        confirm? (prompt/confirm "Are you sure")]

    (if (= true confirm?)
      (do
        (println (esc/clear-screen))
        (println (flf/figlet (str "Hi, " (if (= "M" gender) "Mr." "Ms.")) "starwars"))
        (println (flf/figlet first-name))
        (println (flf/figlet last-name)))
      (meeting))))

(defn -main
  "Main..."
  [& args]

  (prompt/check-list "asd" [{:label "one" :value "1" :checked true} {:label "two" :value "2"}])
  )
