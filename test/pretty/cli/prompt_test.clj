(ns pretty.cli.prompt-test
  (:require [clojure.test :refer :all]
            [pretty.cli.prompt :refer :all]))

(deftest prompt-test
  (testing "Prompt input"
    (with-redefs [read-line (fn [] "test-input")]
      (is (= "test-input" (input "test-question")))))

  (testing "Prompt confirm"
    (with-redefs [read-line (fn [] "y")]
      (is (= true (confirm "test-question"))))
    (with-redefs [read-line (fn [] "Y")]
      (is (= true (confirm "test-question"))))
    (with-redefs [read-line (fn [] "yes")]
      (is (= true (confirm "test-question"))))

    (with-redefs [read-line (fn [] "n")]
      (is (= false (confirm "test-question"))))
    (with-redefs [read-line (fn [] "N")]
      (is (= false (confirm "test-question"))))
    (with-redefs [read-line (fn [] "no")]
      (is (= false (confirm "test-question"))))

    (with-redefs [read-line (fn [] "")]
      (is (= true (confirm "test-question")))
      (is (= true (confirm "test-question" true)))
      (is (= false (confirm "test-question" false))))))
