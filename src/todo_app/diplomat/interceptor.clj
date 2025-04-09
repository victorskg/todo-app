(ns todo-app.diplomat.interceptor
  (:require [clojure.data.json :as json]))

(defn transform-content
  [body content-type]
  (case content-type
    "text/html" body
    "text/plain" body
    "application/edn" (pr-str body)
    "application/json" (json/write-str body)))

(defn coerce-to
  [response content-type]
  (-> response
      (update :body transform-content content-type)
      (assoc-in [:headers "Content-Type"] content-type)))

(def coerce-body-interceptor
  {:name ::coerce-body
   :leave
   (fn [context]
     (let [content-type (get-in context [:response :headers "Content-Type"] "text/plain")]
       (update-in context [:response] coerce-to content-type)))})
