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
 ::cloud-count
 :<- [::clouds]
 (fn [clouds]
   (count (filter identity clouds))))

(re-frame/reg-sub
 ::power-generated
 :<- [::cloud-count]
 (fn [cloud-count]
   (* (- 50 cloud-count)
      10))
 )
