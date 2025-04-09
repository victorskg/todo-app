(ns todo-app.controller.todo
  (:require [schema.core :as s]
            [todo-app.db.in-memory.todo :as db.t]
            [todo-app.logic.todo :as logic.t]
            [todo-app.models.todo :as t]))

(s/defn ^:always-validate create :- t/Todo
  [todo :- t/Todo]
  (db.t/save-todo! todo))

(s/defn ^:always-validate update :- t/Todo
  [todo :- t/Todo]
  (db.t/update-todo! todo))

(s/defn list :- [t/Todo]
  []
  (db.t/find-all-todos))

(s/defn find-by-id :- t/Todo
  [id :- s/Uuid]
  (db.t/find-todo-by-id id))

(s/defn finish :- t/Todo
  [id :- s/Uuid]
  (-> id
      (db.t/find-todo-by-id)
      (logic.t/finish-todo!)
      (db.t/update-todo!)))
