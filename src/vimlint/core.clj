(ns vimlint.core
  (:require [clojure.string :as s]))

(defn -main [fname]
  (let [lines (s/split-lines (slurp fname))]
    "just check indentation for now"
    (prn (every? #(re-find #"^$|^[^\t]+" %) lines))))
