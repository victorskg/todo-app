(ns todo-app.models.todo
  (:require [schema.core :as s]))

(def status
  #{
    :created
    :finished
    })

(s/defschema Status
  (apply s/enum status))

(s/defschema Todo
  {
   (s/optional-key :id)     s/Uuid
   :title                   s/Str
   :description             s/Str
   (s/optional-key :status) Status
   })

(s/defn create-todo :- Todo
  [{:keys [id title description]}]
  (s/validate Todo {
                    :id          (if (nil? id) (random-uuid) (parse-uuid id))
                    :title       title
                    :description description
                    }))
