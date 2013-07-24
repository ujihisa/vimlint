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

(defn read-list-vim-commands []
  (for [line (s/split-lines (slurp "resources/commands.dict"))
        :let [[_ cname _ args] (re-matches #"(\w+)(\s*;\s*)(.*)" line)]]
    {:cname cname :args args}))

(defn indentation [lines depth]
  ; TODO: use depth
  (every? #(re-find #"^$|^[^\t]+" %) lines))

(def vim-expr
  (choice [(<$> (fn [x] [:number x])
                number)
           #_(TODO)]))

(def vim-command
  (<$> (fn [cmdname _ expr]
         [:command cmdname expr])
       (string "echo")
       (many1 whitespace)
       vim-expr))

(defn parse [vim-str]
  (:result (z/parse-once vim-command vim-str)))

(def ^:dynamic *verbose* true)

(defn -main [fname]
  (prn (read-list-vim-commands))
  (let [lines (s/split-lines (slurp fname))]
    "just check indentation for now"
    (if (indentation lines 2)
      (when *verbose*
        (prn "ok"))
      (prn "wrong indentation"))))
