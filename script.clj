#!/usr/bin/env bb

(ns script
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]))

;; (-> (io/reader *in*) line-seq first (str/includes? "m"))
(print "sup")
