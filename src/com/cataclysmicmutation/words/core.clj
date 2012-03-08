(ns com.cataclysmicmutation.words.core
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combinatorics])
  (:use com.cataclysmicmutation.words.trie)
  (:gen-class))

(declare match-seq all-permutations fill-pattern replace-first-item expand-blanks run-loop)

(defn find-valid-words
  "return a seq of words that can be formed from a set of tiles and matching a
given template pattern. The pattern is a string to be interpreted as follows:

'*' is a wildcard that matches any valid letter, any other letter matches the
letter exactly

For the tiles, specify each letter you have in your hand, repeating letters
if you have multiple copies of the letter. Specify blanks as '*' in the string."
  [dict pattern tiles]
  (filter (partial lookup-word dict) (match-seq pattern tiles)))

(defn- match-seq
  "return a lazy sequence of all possible ways to fill out the given pattern
template using the given tile set"
  [pattern tiles]
  (let [holes (count (filter #{\*} pattern)),
        subs (distinct (expand-blanks (all-permutations tiles holes)))]
    (map fill-pattern (repeat pattern) subs)))

(defn- all-permutations
  "generate all permutations of k elements from a set of tiles"
  [tiles k]
  (loop [sets (distinct (combinatorics/combinations tiles k)), results []]
    (if (empty? sets)
      results
      (recur (next sets) (concat (filter #(not (some #{%} results))
                                         (distinct (combinatorics/permutations (first sets))))
                                 results)))))

(defn- expand-blanks
  "takes a seq of tile sets and produces another seq of tile sets where each
blank has been expanded all possible ways"
  [tiles-seq]
  (loop [tiles-seq tiles-seq, result []]
    (if (seq tiles-seq)
      (let [tiles (first tiles-seq)]
        (if (some #{\*} tiles)
          (recur (concat (map (partial replace-first-item tiles \*)
                              (for [c "ABCDEFGHIJKLMNOPQRSTUVWXYZ"] c))
                         (next tiles-seq))
                 result)
          (recur (next tiles-seq)
                 (conj result tiles))))
      result)))

(defn- fill-pattern
  "take a pattern string with k holes and a tile string of length k and substitute
the tiles in places of the holes"
  [pattern tiles]
  (if (seq tiles)
    (recur (str/replace-first pattern \* (first tiles)) (rest tiles))
    pattern))

(defn- replace-first-item
  "construct a new list with the first occurrence of item replaced by rep"
    [v item rep]
  (loop [v v, res []]
    (if (seq v)
      (if (= (first v) item)
        (concat (reverse res) (cons rep (next v)))
        (recur (next v) (cons (first v) res)))
      (reverse res))))

(defn run-loop
  "load the default dictionary and allow user to issue queries"
  []
  (print "loading dictionary...") (flush)
  (let [dict (load-word-file "res/scrabble_dict.txt")]
    (loop []
      (print "\nEnter a pattern and a tile set: ") (flush)
      (let [in (read-line)
            [patt tiles] (str/split in #"\s")]
        (println "searching for words...") (flush)
        (doseq [word (find-valid-words dict patt tiles)]
          (println word))
        (flush)
        (recur)))))

(defn -main
  [& args]
  (run-loop))
