(ns pretty.cli.figlet-test
  (:require [clojure.test :refer :all]
            [pretty.cli.figlet :refer :all]))

(deftest figlet-test
  (testing "Figlet string creation"
    (is (= (figlet "T") "  _____ \n |_   _|\n   | |  \n   | |  \n   |_|  \n        \n"))
    (is (= (figlet "T" "standard") "  _____ \n |_   _|\n   | |  \n   | |  \n   |_|  \n        \n"))
    (is (= (figlet "T" "3x5") "    \n### \n #  \n #  \n #  \n #  \n"))))
