(defproject com.cataclysmicmutation.words "0.1"
  :description "Suggest words for Scrabble-like games."
  :dependencies [[clojure "1.2.0"]
                 [clojure-contrib "1.2.0"]]
  :dev-dependencies [[swank-clojure "1.3.0"]
                     [marginalia "0.6.1"]
					 [cake-marginalia "0.6.0"]]
  :tasks [cake-marginalia.tasks]
  :aot [com.cataclysmicmutation.words.core]
  :main com.cataclysmicmutation.words.core)
