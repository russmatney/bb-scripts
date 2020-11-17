

(require '[babashka.process :refer [$ process check]])

;; (-> ($ echo hi)
;;     check
;;     :out
;;     slurp)


;; (->
;;   (process '["fzf"]
;;            {:error :inherit
;;             :in    :inherit})
;;   check
;;   :out
;;   slurp
;;   )

(require '[clojure.java.io :as io])
;; (import 'java.lang.ProcessBuilder$Redirect)

;; (defmacro with-filter
;;   [command & forms]
;;   `(let [sh#  (or (System/getenv "SHELL") "sh")
;;          pb#  (doto (ProcessBuilder. [sh# "-c" ~command])
;;                 (.redirectError
;;                   (ProcessBuilder$Redirect/to (io/file "/dev/tty"))))
;;          p#   (.start pb#)
;;          in#  (io/reader (.getInputStream p#))
;;          out# (io/writer (.getOutputStream p#))]
;;      (binding [*out* out#]
;;        (try ~@forms (.close out#) (catch Exception e#)))
;;      (take-while identity (repeatedly #(.readLine in#)))))

;; (defmacro with-bb-filter
;;   [command & forms]
;;   `(let [cmd-proc# (process '[~(System/getenv "SHELL") "-c" ~command]
;;                             {:err (io/file "/dev/tty")})
;;          cmd-in#   (io/reader (:in cmd-proc#))
;;          cmd-out#  (io/writer (:out cmd-proc#))]
;;      (binding [*out* cmd-out#]
;;        (try ~@forms (.close cmd-out#) (catch Exception e#)))
;;      (take-while identity (repeatedly #(.readLine cmd-in#)))))

;; (with-bb-filter "fzf -m"
;;   (dotimes [n 50]
;;     (println n)
;;     (Thread/sleep 5)))

(let [shell    (System/getenv "SHELL")
      cmd-proc (process [shell "-c" "fzf -m"]
                        {:err (io/file "/dev/tty")
                         :in  :inherit})
      cmd-in   (io/reader (.getInputStream cmd-proc))
      cmd-out  (io/writer (.getOutputStream cmd-proc))]

  (binding [*out* cmd-out]
    (try (dotimes [n 10]
           (println n))
         (.close cmd-out)
         (catch Exception e
           (println e))))

  (take-while identity (repeatedly #(.readLine cmd-in))))



;; (def tail (process '[tail -f "log.txt"] {:err :inherit}))

;; (def cat-and-grep
;;   (-> (process '[cat]      {:err :inherit})
;;       (process '[grep "5"] {:out :inherit
;;                             :err :inherit})))

;; (binding [*in*  (io/reader (:out tail))
;;           *out* (io/writer (:in cat-and-grep))]
;;   (loop []
;;     (when-let [x (read-line)]
;;       (println x)
;;       (recur))))

;; (->
;;   (process '["fzf"]
;;            {:error :inherit
;;             :in    :inherit})
;;   check
;;   :out
;;   slurp
;;   )
