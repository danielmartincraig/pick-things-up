(ns pick-things-up.events
  (:require
   [re-frame.core :as re-frame]
   [pick-things-up.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [vimsical.re-frame.cofx.inject :as inject]
   [pick-things-up.subs :as subs]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(defn dispatch-timer-event
  []
  (re-frame/dispatch [::update-game-state]))

(defonce do-timer (js/setInterval dispatch-timer-event 200))

(re-frame/reg-event-fx
 ::toggle-cloud
 (fn [{:keys [db]} [_ cloudId]]
   (let [clouds (:clouds db)
         left (if (<= cloudId 0) 0 (dec cloudId))
         right (if (>= cloudId 49) 49 (inc cloudId))
         neighbors (subvec clouds left (inc right))
         cloudy-neighbors (filter identity neighbors)
         cloudy? (> (count (filter identity neighbors)) 1)]
     {:db (if cloudy? (update-in db [:clouds cloudId] not) db)
      :fx (if cloudy? [[:dispatch [::toggle-cloud left]]
                       [:dispatch [::toggle-cloud right]]] [])})))

(re-frame/reg-event-fx
 ::force-toggle-random-cloud
 (fn [{:keys [db]} event]
   (let [cloudId (rand-int 50)]
     {:db (update-in db [:clouds cloudId] not)})))

(re-frame/reg-event-fx
 ::generate-solar-power
 [(re-frame/inject-cofx ::inject/sub [::subs/power-generated])]
 (fn [{db :db power-generated ::subs/power-generated } event]
   {:db (update db :total-power-generated (partial + power-generated))}))

(re-frame/reg-event-fx
 ::update-game-state
 (fn [{:keys [db]} event]
   (let [cloudId (rand-int 50)]
     {:fx [[:dispatch [::force-toggle-random-cloud]]
           [:dispatch [::generate-solar-power]]]}
     )))
