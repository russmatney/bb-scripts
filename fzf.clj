;; https://github.com/borkdude/babashka/blob/master/examples/fzf.clj

(require '[babashka.process :refer [process]])

(defn fzf [s]
  (let [proc (process ["fzf" "-m"]
                      {:in  s :err :inherit
                       :out :string})]
    (:out @proc)))

(fzf (slurp *in*))
