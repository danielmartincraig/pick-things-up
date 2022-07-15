(ns pick-things-up.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::clouds
 (fn [db]
   (:clouds db)))

(re-frame/reg-sub
 ::cloud
 :<- [::clouds]
 (fn [clouds [_ cloudId]]
   (nth clouds cloudId)
   ))

(re-frame/reg-sub
 ::neighbors
 :<- [::clouds]
 (fn [clouds [_ cloudId]]
   (subvec clouds (dec cloudId) (+ cloudId 2))))
