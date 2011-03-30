(ns com.cataclysmicmutation.words.core
  (:require [clojure.string :as str]
            [clojure.contrib.combinatorics :as combinatorics])
  (:use com.cataclysmicmutation.words.trie))

(declare match-seq all-permutations fill-pattern replace-item expand-blanks)

(defn find-valid-words
  "return a seq of words that can be formed from a set of tiles and matching a
given template pattern. The pattern is a string to be interpreted as follows:

* is a wildcard that matches any valid letter, any other letter matches the
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
        subs (expand-blanks (all-permutations tiles holes))]
    (map fill-pattern (repeat pattern) subs)))

(defn- all-permutations
  "generate all permutations of k elements from a set of tiles"
  [tiles k]
  (loop [sets (combinatorics/combinations tiles k), results []]
    (if (empty? sets)
      results
      (recur (next sets) (concat (combinatorics/permutations (first sets)) results)))))

(defn- expand-blanks
  [tiles-seq]
  (loop [tiles-seq tiles-seq, result []]
    (if (seq tiles-seq)
      (let [tiles (first tiles-seq)]
        (if (some #{\*} tiles)
          (recur (next tiles-seq)
                 (concat result (map (partial replace-item tiles \*)
                                     (for [c "ABCDEFGHIJKLMNOPQRSTUVWXYZ"] c))))
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

(defn- replace-item
  "construct a new list with each occurrence of item replaced by rep"
  [v item rep]
  (loop [v v, res []]
    (if (seq v)
      (if (= (first v) item)
        (recur (next v) (cons rep res))
        (recur (next v) (cons (first v) res)))
      (vec (reverse res)))))
