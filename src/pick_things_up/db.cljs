(ns pick-things-up.db)

(def default-db
  {:name "re-frame"
   :clouds (apply vector (take 50 (repeat false)))
   })
