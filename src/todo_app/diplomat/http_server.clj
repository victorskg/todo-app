(ns todo-app.diplomat.http-server
  (:require [io.pedestal.http.body-params :as http.body-params]
            [io.pedestal.http.route :as route]
            [todo-app.adapters.todo :as adapters.t]
            [todo-app.controller.todo :as controller.t]
            [todo-app.diplomat.interceptor :as interceptor]))

(defn create-todo
  [request]
  (let [body         (:json-params request)
        created-todo (-> body
                         (adapters.t/todo-wire->model)
                         (controller.t/create)
                         (adapters.t/todo-model->wire))]
    {:status  201
     :body    created-todo
     :headers {"Content-Type" "application/json"}}))

(defn get-all-todos
  [_]
  (let [todos (map #(adapters.t/todo-model->wire %) (controller.t/list))]
    {:status  200
     :body    todos
     :headers {"Content-Type" "application/json"}}))

(defn get-todo-by-id
  [request]
  (let [id   (get-in request [:path-params :todo-id])
        uuid (parse-uuid id)
        todo (-> uuid
                 (controller.t/find-by-id)
                 (adapters.t/todo-model->wire))]
    {:status  200
     :body    todo
     :headers {"Content-Type" "application/json"}}))

(defn update-todo
  [request]
  (let [body (:json-params request)
        todo (-> body
                 (adapters.t/todo-wire->model)
                 (controller.t/update)
                 (adapters.t/todo-model->wire))]
    {:status  200
     :body    todo
     :headers {"Content-Type" "application/json"}}))

(defn finish-todo
  [request]
  (let [id   (get-in request [:path-params :todo-id])
        uuid (parse-uuid id)
        todo (-> uuid
                 (controller.t/finish)
                 (adapters.t/todo-model->wire))]
    {:status  200
     :body    todo
     :headers {"Content-Type" "application/json"}}))

(def common-interceptors [(http.body-params/body-params) interceptor/coerce-body-interceptor])

(def routes
  (route/expand-routes
   #{["/todo" :post (conj common-interceptors create-todo) :route-name :create-todo]
     ["/todo" :get [interceptor/coerce-body-interceptor get-all-todos] :route-name :get-all-todos]
     ["/todo/:todo-id" :get [interceptor/coerce-body-interceptor get-todo-by-id] :route-name :get-todo-by-id]
     ["/todo/:todo-id" :put (conj common-interceptors update-todo) :route-name :update-todo]
     ["/todo/:todo-id/finish" :patch [interceptor/coerce-body-interceptor finish-todo] :route-name :finish-todo]}))
