(ns vimlint.core
  (:require [clojure.string :as s]))

(defn read-list-vim-functions []
  (for [line (s/split-lines (slurp "resources/functions.dict"))
        :let [[_ fname args] (re-matches #"(\w+)(.*)" line)]]
    {:fname fname :args args}))

(defn indentation [lines depth]
  ; TODO: use depth
  (every? #(re-find #"^$|^[^\t]+" %) lines))

(defn -main [fname]
  (prn (read-list-vim-functions))
  #_(let [lines (s/split-lines (slurp fname))]
    "just check indentation for now"
    (prn (indentation lines 2))))
