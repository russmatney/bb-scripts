(require '[bencode.core :as b])

(defn nrepl-eval [port expr]
  (let [s     (java.net.Socket. "localhost" port)
        out   (.getOutputStream s)
        in    (java.io.PushbackInputStream. (.getInputStream s))
        _     (b/write-bencode out {"op" "eval" "code" expr})
        bytes (get (b/read-bencode in) "value")
        ]
    (String. bytes)))

(nrepl-eval 7888 "(+ 1 2 3)")
