(ns pick-things-up.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com :refer [at]]
   [pick-things-up.subs :as subs]
   [pick-things-up.events :as events]
   ))

(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :src   (at)
     :label (str "Hello from " @name)
     :level :level1]))

(defn cloud [cloudId]
  (let [cloud (re-frame/subscribe [::subs/cloud cloudId])]
    [re-com/md-icon-button
     :md-icon-name (if @cloud "zmdi-cloud" "zmdi-cloud-outline")
     :on-click (fn [] (re-frame/dispatch [::events/toggle-cloud cloudId]))]))

(defn clouds []
  [re-com/h-box
   :src      (at)
   :width    "100%"
   :children [(for [cloudId (range 50)]
                [cloud cloudId])]])

(defn main-panel []
  [re-com/v-box
   :src      (at)
   :height   "100%"
   :children [[clouds]]])
