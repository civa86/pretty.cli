(ns pretty.cli.colors-test
  (:require [clojure.test :refer :all]
            [pretty.cli.colors :refer :all]))

(deftest foreground-colors-test
  (testing "[FG COLOR] black"
    (is (= (black "test") "\033[30mtest\033[0m")))

  (testing "[FG COLOR] red"
    (is (= (red "test") "\033[31mtest\033[0m")))

  (testing "[FG COLOR] green"
    (is (= (green "test") "\033[32mtest\033[0m")))

  (testing "[FG COLOR] yellow"
    (is (= (yellow "test") "\033[33mtest\033[0m")))

  (testing "[FG COLOR] blue"
    (is (= (blue "test") "\033[34mtest\033[0m")))

  (testing "[FG COLOR] magenta"
    (is (= (magenta "test") "\033[35mtest\033[0m")))

  (testing "[FG COLOR] cyan"
    (is (= (cyan "test") "\033[36mtest\033[0m")))

  (testing "[FG COLOR] white"
    (is (= (white "test") "\033[37mtest\033[0m"))))

(deftest background-colors-test
  (testing "[BG COLOR] black"
    (is (= (black-bg "test") "\033[40mtest\033[0m")))

  (testing "[BG COLOR] red"
    (is (= (red-bg "test") "\033[41mtest\033[0m")))

  (testing "[BG COLOR] green"
    (is (= (green-bg "test") "\033[42mtest\033[0m")))

  (testing "[BG COLOR] yellow"
    (is (= (yellow-bg "test") "\033[43mtest\033[0m")))

  (testing "[BG COLOR] blue"
    (is (= (blue-bg "test") "\033[44mtest\033[0m")))

  (testing "[BG COLOR] magenta"
    (is (= (magenta-bg "test") "\033[45mtest\033[0m")))

  (testing "[BG COLOR] cyan"
    (is (= (cyan-bg "test") "\033[46mtest\033[0m")))

  (testing "[BG COLOR] white"
    (is (= (white-bg "test") "\033[47mtest\033[0m"))))
