(ns com.cataclysmicmutation.words.test-trie
  (:use com.cataclysmicmutation.words.trie clojure.test))

(deftest trie-test
  (let [t (add-words {} ["A" "AH" "AM" "AN" "BAT" "CAT" "DOG" "DO" "DOGS" "ANY" "ANT"])]
    (is (lookup-word t "A"))
    (is (lookup-word t "AH"))
    (is (lookup-word t "AM"))
    (is (lookup-word t "AN"))
    (is (lookup-word t "BAT"))
    (is (lookup-word t "CAT"))
    (is (lookup-word t "DOG"))
    (is (lookup-word t "DO"))
    (is (lookup-word t "DOGS"))
    (is (lookup-word t "ANY"))
    (is (lookup-word t "ANT"))
    (is (not (lookup-word t "AA")))
    (is (not (lookup-word t "BA")))
    (is (not (lookup-word t "X")))
    (is (not (lookup-word t "PACHYDERM")))
    (is (not (lookup-word t "D")))
    (is (not (lookup-word t "ANTE")))))

