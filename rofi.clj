
(require '[babashka.process :refer [process]])

(defn rofi [s]
  (let [proc (process
               ["rofi" "-i" "-dmenu" "-mesg" "Some Message" "-sync" "-p" "*"]
               {:in  s :err :inherit
                :out :string})]
    (:out @proc)))

(rofi (slurp *in*))

