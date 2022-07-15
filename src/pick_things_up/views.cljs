(ns pick-things-up.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com :refer [at]]
   [pick-things-up.subs :as subs]
   [pick-things-up.events :as events]
   [clojure.string :as str]
   ))

(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :src   (at)
     :label (str "Hello from " @name)
     :level :level1]))

(defn power-generated []
  (let [power-generated (re-frame/subscribe [::subs/power-generated])]
    [:p @power-generated]))

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
                ^{:key cloudId} [cloud cloudId])]])

(defn cloud-count []
  (let [cloud-count (re-frame/subscribe [::subs/cloud-count])]
    [:p @cloud-count]))

(defn power-meter []
  (let [maxMw 500
        power-generated (re-frame/subscribe [::subs/power-generated])]
    (fn []
      (let [hand-angle (-> @power-generated
                           (/ maxMw)
                           (* (* 1 js/Math.PI))
                           (+ js/Math.PI))
            hand-endpoint [(* 85 (js/Math.cos hand-angle))
                           (* 85 (js/Math.sin hand-angle))]]
        [:div
         [:svg
          {:style {:width 200
                   :height 200}
           :view-box "-100 -100 200 200"}
          [:circle {:r 90
                    :style {:fill "white"
                            :stroke "black"
                            :stroke-width 3}}]
          [:path {:stroke "black"
                  :d (str "M 0 0 L " (clojure.string/join " " hand-endpoint))}]]]))))

(defn main-panel []
  [re-com/v-box
   :src      (at)
   :height   "100%"
   :children [[clouds]
              #_[power-generated]
              [power-meter]]])
