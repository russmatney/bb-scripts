(ns print-date)

(def now (java.time.ZonedDateTime/now))
(def LA-timezone (java.time.ZoneId/of "America/Los_Angeles"))
(def LA-time (.withZoneSameInstant now LA-timezone))
(def pattern (java.time.format.DateTimeFormatter/ofPattern "HH:mm"))
(println "time in LA")
(println (.format LA-time pattern))
