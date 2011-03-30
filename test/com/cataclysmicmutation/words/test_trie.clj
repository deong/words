(ns com.cataclysmicmutation.words.test-trie
  (:use com.cataclysmicmutation.words.trie clojure.test))

(deftest trie-test
  (let [t (add-words {} ["a" "ah" "am" "an" "bat" "cat" "dog" "do" "dogs" "any" "ant"])]
    (is (lookup-word t "a"))
    (is (lookup-word t "ah"))
    (is (lookup-word t "am"))
    (is (lookup-word t "an"))
    (is (lookup-word t "bat"))
    (is (lookup-word t "cat"))
    (is (lookup-word t "dog"))
    (is (lookup-word t "do"))
    (is (lookup-word t "dogs"))
    (is (lookup-word t "any"))
    (is (lookup-word t "ant"))
    (is (not (lookup-word t "aa")))
    (is (not (lookup-word t "ba")))
    (is (not (lookup-word t "x")))
    (is (not (lookup-word t "pachyderm")))
    (is (not (lookup-word t "d")))
    (is (not (lookup-word t "ante")))))

