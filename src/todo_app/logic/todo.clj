(ns todo-app.logic.todo
  (:require [schema.core :as s]
            [todo-app.models.todo :as t]))

(s/defn ^:always-validate finish-todo! :- t/Todo
  [todo :- t/Todo]
  (assoc todo :status :finished))
