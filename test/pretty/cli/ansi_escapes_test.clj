(ns pretty.cli.ansi_escapes_test
  (:require [clojure.test :refer :all]
            [clojure.pprint]
            [pretty.cli.ansi-escapes :refer :all]))

(deftest ansi-escapes-test
  (testing "cursor-to"
    (is (= (cursor-to) "\033[H"))
    (is (= (cursor-to 1) "\033[2G"))
    (is (= (cursor-to 1 2) "\033[3;2H")))

  (testing "cursor-move"
    (is (= (cursor-move 1 2) "\033[1C\033[2B"))
    (is (= (cursor-move -1 2) "\033[1D\033[2B"))
    (is (= (cursor-move 1 -2) "\033[1C\033[2A"))
    (is (= (cursor-move -1 -2) "\033[1D\033[2A")))

  (testing "cursor-up"
    (is (= (cursor-up) "\033[1A"))
    (is (= (cursor-up 5) "\033[5A")))

  (testing "cursor-down"
    (is (= (cursor-up) "\033[1B"))
    (is (= (cursor-up 5) "\033[5B")))

  (testing "cursor-forward"
    (is (= (cursor-up) "\033[1C"))
    (is (= (cursor-up 5) "\033[5C")))

  (testing "cursor-backward"
    (is (= (cursor-up) "\033[1D"))
    (is (= (cursor-up 5) "\033[5D")))
  )
