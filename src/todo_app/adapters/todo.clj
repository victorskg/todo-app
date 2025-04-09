(ns todo-app.adapters.todo
  (:require [schema.core :as s]
            [todo-app.models.todo :as t]
            [todo-app.wire.in.todo :as in.t]
            [todo-app.wire.out.todo :as out.t]))

(s/defn todo-wire->model :- t/Todo
  [todo-in :- in.t/Todo]
  (t/create-todo todo-in))

(s/defn todo-model->wire :- out.t/Todo
  [{:keys [id title description status]} :- t/Todo]
  {
   :id          id
   :title       title
   :description description
   :status      status
   })
