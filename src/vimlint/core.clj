(ns vimlint.core
  (:require [clojure.string :as s]))

(defn indentation [lines depth]
  ; TODO: use depth
  (every? #(re-find #"^$|^[^\t]+" %) lines))

(defn -main [fname]
  (let [lines (s/split-lines (slurp fname))]
    "just check indentation for now"
    (prn (indentation lines 2))))
