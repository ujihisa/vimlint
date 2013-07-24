(ns vimlint.core
  (:require [clojure.string :as s]
            [zetta.core :as z])
  (:use [zetta.core :only [<$> <* *> >>]])
  (:use
    [zetta.combinators
      :only
      [sep-by around many many1 choice]]
    [zetta.parser.seq
      :only
      [string not-char number whitespace]]))

(defn read-list-vim-functions []
  (for [line (s/split-lines (slurp "resources/functions.dict"))
        :let [[_ fname args] (re-matches #"(\w+)(.*)" line)]]
    {:fname fname :args args}))

(defn indentation [lines depth]
  ; TODO: use depth
  (every? #(re-find #"^$|^[^\t]+" %) lines))

(def vim-command
  (<$> (fn [cmdname _ expr]
         [:command cmdname [:number expr]])
       (string "echo")
       (many1 whitespace)
       (string "123")))

(defn parse [vim-str]
  (:result (z/parse-once vim-command vim-str)))

(defn -main [fname]
  (prn (read-list-vim-functions))
  #_(let [lines (s/split-lines (slurp fname))]
    "just check indentation for now"
    (prn (indentation lines 2))))
