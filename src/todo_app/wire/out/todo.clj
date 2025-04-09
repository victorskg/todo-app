(ns todo-app.wire.out.todo
  (:require [schema.core :as s]
            [todo-app.models.todo :as t]))

(s/defschema Todo
  {
   :id          s/Uuid
   :title       s/Str
   :description s/Str
   :status      t/Status
   })
