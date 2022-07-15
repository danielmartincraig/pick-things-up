(ns pick-things-up.events
  (:require
   [re-frame.core :as re-frame]
   [pick-things-up.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::toggle-cloud
 [(re-frame/path [:clouds])]
 (fn-traced [clouds [_ cloud-id]]
            (update clouds cloud-id not)
            ))
