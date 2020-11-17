

(require '[babashka.process :refer [$ process check]])

;; (-> ($ echo hi)
;;     check
;;     :out
;;     slurp)


;; (->
;;   (process '["echo" "hi"])
;;   check
;;   :out
;;   slurp
;;   )

(require '[clojure.java.io :as io])
(import 'java.lang.ProcessBuilder$Redirect)

(defmacro with-filter
  [command & forms]
  `(let [sh#  (or (System/getenv "SHELL") "sh")
         pb#  (doto (ProcessBuilder. [sh# "-c" ~command])
                (.redirectError
                  (ProcessBuilder$Redirect/to (io/file "/dev/tty"))))
         p#   (.start pb#)
         in#  (io/reader (.getInputStream p#))
         out# (io/writer (.getOutputStream p#))]
     (binding [*out* out#]
       (try ~@forms (.close out#) (catch Exception e#)))
     (take-while identity (repeatedly #(.readLine in#)))))

(with-filter "fzf -m"
  (dotimes [n 50]
    (println n)
    (Thread/sleep 5)))
