(defproject com.cataclysmicmutation.words "0.1"
  :description "Suggest words for Scrabble-like games."
  :dependencies [[clojure "1.3.0"]
  				 [org.clojure/math.combinatorics "0.0.3-SNAPSHOT"]]
  :dev-dependencies [[marginalia "0.8.0-SNAPSHOT"]]
  :aot [com.cataclysmicmutation.words.core]
  :main com.cataclysmicmutation.words.core)
