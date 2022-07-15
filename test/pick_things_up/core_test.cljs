(ns pick-things-up.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [pick-things-up.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
