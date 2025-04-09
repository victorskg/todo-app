(ns todo-app.db.in-memory.todo
  (:require [schema.core :as s]
            [todo-app.models.todo :as t]))

(s/def ^:private todos (ref []))

(s/defn ^:always-validate save-todo! :- t/Todo
  [todo :- t/Todo]
  (let [todo-to-create (assoc todo :status :created)]
    (dosync
     (alter todos conj todo-to-create))
    todo-to-create))

(s/defn find-todo-by-id :- (s/maybe t/Todo)
  [id :- s/Uuid]
  (->> @todos
       (filter #(= (:id %) id))
       first))

(s/defn ^:always-validate update-todo! :- t/Todo
  [todo :- t/Todo]
  (let [id           (:id todo)
        db-todo      (find-todo-by-id id)
        updated-todo (merge db-todo todo)]
    (if (= nil db-todo)
      (throw (IllegalArgumentException. (format "Todo with id %s not found" id)))
      (dosync (alter todos (constantly (map #(if (= (:id %) id) updated-todo %) @todos)))))
    updated-todo))

(s/defn find-all-todos :- [t/Todo]
  []
  @todos)
