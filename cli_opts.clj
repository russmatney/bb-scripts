#!/usr/bin/env bb

(require '[clojure.tools.cli :refer [parse-opts]])

(def cli-opts
  ""
  [["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-h" "--help"]])

(let [opts (parse-opts *command-line-args* cli-opts)]
  (prn (:options opts))
  opts)
