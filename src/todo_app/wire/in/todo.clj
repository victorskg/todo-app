(ns todo-app.wire.in.todo
  (:require [schema.core :as s]))

(s/defschema Todo
             {
              (s/optional-key :id) s/Str
              :title               s/Str
              :description         s/Str
              })
