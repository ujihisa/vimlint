(ns vimlint.core-test
  (:require [clojure.test :refer :all]
            [vimlint.core :refer :all]))

(deftest a-test
  (testing "trivial echo command with literals"
    (is (= (vimlint.core/parse "echo 123") [:command "echo" [:number 123]]))))
