(ns com.cataclysmicmutation.words.trie
  (:require [clojure.string :as str])
  (:import [java.io BufferedReader FileReader]))

(declare keyword-char)

(defn add-word
  "add a word to an existing trie"
  [trie word]
  (let [keyw (keyword-char (first word)),
        node (keyw trie)]
    (assoc trie keyw
           {:complete (or (:complete node) (nil? (next word))),
            :children (if (nil? (next word))
                        ;; at end of word, just return the current child node
                        (:children node)
                        ;; otherwise, recursively add rest of the word to the correct subtrie
                        (add-word (:children node)
                                  (apply str (next word))))})))


(defn lookup-word
  "determine if a word is in a given trie"
  [trie word]
  (loop [trie trie, word (str/upper-case word)]
    (let [kw (keyword-char (first word)),
          child (:children (kw trie))]
      (if (next word)
        (recur child (apply str (rest word)))
        (:complete (kw trie))))))

(defn add-words
  "add an entire collection of words to a trie"
  [trie words]
  (reduce add-word trie words))

(defn load-word-file
  "load a file of valid words into a trie"
  [word-file]
  (add-words {} (map str/upper-case (str/split-lines (slurp word-file)))))

(defn- keyword-char
  "make a keyword out of a given character"
  [c]
  (keyword (.toString c)))

