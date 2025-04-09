(ns todo-app.server
  (:require [io.pedestal.http :as http]
            [todo-app.diplomat.http-server :as http-server]))

(defn create-server
  []
  (http/create-server
   {::http/routes http-server/routes
    ::http/type   :jetty
    ::http/port   8890
    ::http/join?  false}))

; For interactive development
(defonce server (atom nil))

(defn start-dev
  []
  (reset! server (http/start (create-server)))
  (println "Server started"))

(defn stop-dev
  []
  (http/stop @server)
  (println "Server stopped"))

(defn restart-dev
  []
  (stop-dev)
  (start-dev))

(defn -main
  []
  (http/start (create-server)))