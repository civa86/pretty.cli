(ns pretty.cli
  (:require [clojure.pprint]
            [pretty.terminal.screen :as screen]
            [pretty.cli.ansi-escapes :as esc]
            [pretty.cli.figlet :as flf]
            [pretty.cli.prompt :as prompt])
  (:import jline.Terminal))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (esc/clear-screen))
  (println (flf/figlet "TEST"))
  (let [name (prompt/input "name")
        surname (prompt/input "surname")]
    (println (str "Hi " name " " surname))
    )
  ;(let [term (Terminal/getTerminal)]
  ;  (println "INIT")
  ;  (println "asdsadasdad asd asd sad")
  ;  (println "asdasdsadasd asd asd asd")
  ;
  ;  (while true
  ;    (let [pressed (.readCharacter term System/in)]
  ;      (if (= 27 pressed)
  ;        (let [next (.readCharacter term System/in)]
  ;          (if (= 91 next)
  ;            (let [last (.readCharacter term System/in)]
  ;              (cond
  ;                (= 65 last) (do
  ;                              (println (esc/cursor-up 2))
  ;                              (println "asd")
  ;                              ;(screen/cursor-save!)
  ;                              ;(screen/cursor-up! 3)
  ;                              ;(screen/delete-line! 2)
  ;                              ;(screen/cursor-restore!)
  ;                              )
  ;                (= 66 last) (println "DOWN")
  ;
  ;                )
  ;
  ;              )
  ;
  ;            )
  ;
  ;
  ;          )
  ;      )
  ;
  ;
  ;    ))
  ;
  ;)
  )
