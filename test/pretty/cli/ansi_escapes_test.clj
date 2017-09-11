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
    (is (= (cursor-down) "\033[1B"))
    (is (= (cursor-down 5) "\033[5B")))

  (testing "cursor-forward"
    (is (= (cursor-forward) "\033[1C"))
    (is (= (cursor-forward 5) "\033[5C")))

  (testing "cursor-backward"
    (is (= (cursor-backward) "\033[1D"))
    (is (= (cursor-backward 5) "\033[5D")))

  (testing "cursor-left"
    (is (= (cursor-left) "\033[G")))

  (testing "cursor-position"
    (is (= (cursor-position) "\033[6n")))

  (testing "cursor-position-save"
    (is (= (cursor-position-save) "\033[s")))

  (testing "cursor-position-restore"
    (is (= (cursor-position-restore) "\033[u")))

  (testing "cursor-next-line"
    (is (= (cursor-next-line) "\033[E")))

  (testing "cursor-prev-line"
    (is (= (cursor-prev-line) "\033[F")))

  (testing "cursor-hide"
    (is (= (cursor-hide) "\033[?25l")))

  (testing "cursor-show"
    (is (= (cursor-show) "\033[?25h")))

  (testing "erase-end-line"
    (is (= (erase-end-line) "\033[K")))

  (testing "erase-start-line"
    (is (= (erase-start-line) "\033[1K")))

  (testing "erase-line"
    (is (= (erase-line) "\033[2K")))

  (testing "erase-down"
    (is (= (erase-down) "\033[J")))

  (testing "erase-up"
    (is (= (erase-up) "\033[1J")))

  (testing "erase-screen"
    (is (= (erase-screen) "\033[2J")))

  (testing "scroll-up"
    (is (= (scroll-up) "\033[S")))

  (testing "scroll-down"
    (is (= (scroll-down) "\033[T")))

  (testing "clear-screen"
    (is (= (clear-screen) "\u001Bc")))

  (testing "beep"
    (is (= (beep) "\0007"))))
