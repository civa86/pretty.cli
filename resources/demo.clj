(use '[leiningen.exec :only (deps)])
(deps '[[org.clojars.civa86/pretty.cli "0.6.2"]])

(require  '[pretty.cli.ansi-escapes :as esc]
          '[pretty.cli.colors :as c]
          '[pretty.cli.figlet :as flf])

(defn try-figlet
  "Demo usage of pretty.cli.figlet"
  []
  (println (c/cyan (flf/figlet "Demo")))
  (println (c/cyan (flf/figlet "Demo" "starwars"))))

(try-figlet)
