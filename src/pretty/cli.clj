(ns pretty.cli
  (:require [clojure.pprint]
            [pretty.terminal.screen :as screen])
  (:import jline.Terminal))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (let [term (Terminal/getTerminal)]
    (println "INIT")
    (println "asdsadasdad")
    (println "asdasdsadasd")

    (while true
      (let [pressed (.readCharacter term System/in)]
        (if (= 27 pressed)
          (let [next (.readCharacter term System/in)]
            (if (= 91 next)
              (let [last (.readCharacter term System/in)]
                (cond
                  (= 65 last) (do

                                (screen/clear!)
                                ;(println "\033[K")
                                )
                  (= 66 last) (println "DOWN")

                  )

                )

              )


            )
        )


      ))

  ))
