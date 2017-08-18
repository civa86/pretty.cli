(defproject pretty.cli "0.1.0-SNAPSHOT"
  :description "Clojure utilities to create amazing Command Line Interfaces"
  :url "https://github.com/civa86/pretty.cli"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.github.lalyos/jfiglet "0.0.8"]
                 [jline "0.9.94"]]
  ;TODO remove main...
  :main ^:skip-aot pretty.cli
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.18.1"]]}})
