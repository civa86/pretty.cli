(defproject org.clojars.civa86/pretty.cli "0.4.0"
  :description "Clojure utilities to create amazing Command Line Interfaces"
  :url "https://github.com/civa86/pretty.cli"
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [prismatic/schema "1.1.6"]
                 [com.github.lalyos/jfiglet "0.0.8"]
                 [jline "0.9.94"]
                 [codox-theme-rdash "0.1.2"]]
  :codox {:project {:name "Pretty CLI"}
          :metadata {:doc/format :markdown}
          :themes [:rdash]
          :html {:namespace-list :flat}
          :source-uri "https://github.com/civa86/pretty.cli/blob/v{version}/{filepath}#L{line}"}
  :deploy-repositories [["clojars" {:url           "https://clojars.org/repo/"
                                    :username      :env/clojars_user
                                    :password      :env/clojars_passwd
                                    :sign-releases false}]]
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.18.1"]
                             [lein-codox "0.10.3"]]}})

