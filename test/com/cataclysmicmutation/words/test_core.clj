(ns com.cataclysmicmutation.words.test-core
  (:use com.cataclysmicmutation.words.core
        com.cataclysmicmutation.words.trie
        clojure.test))

(deftest test-find-valid-words
  (let [dict (load-word-file "res/scrabble_dict.txt")]
    (is (= (count (find-valid-words dict "*X**" "AEIRSTL")) 6))
    (is (= (count (find-valid-words dict "APP***" "AEIRSTL")) 7))
    (is (= (count (find-valid-words dict "Q***" "AOTIDUP")) 10))
    (is (= (count (find-valid-words dict "Q***" "AOTIDZP")) 2))
    (is (= (find-valid-words dict "Q****" "AOTIDZP") []))))
