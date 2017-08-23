(defproject org.clojars.civa86/pretty.cli "0.3.0"
  :description "Clojure utilities to create amazing Command Line Interfaces"
  :url "https://github.com/civa86/pretty.cli"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [prismatic/schema "1.1.6"]
                 [com.github.lalyos/jfiglet "0.0.8"]
                 [jline "0.9.94"]]
  :deploy-repositories [["clojars" {:url "https://clojars.org/repo/"
                                     :username :env/clojars_user
                                     :password :env/clojars_passwd}]]
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.18.1"]
                             [lein-codox "0.10.3"]]}})
