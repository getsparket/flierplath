(ns re-navigate.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :nav/tab-state
  (fn [db _]
    (:nav/tab-state db)))

(reg-sub
  :nav/stack-state
  (fn [db [_ route-name]]
    (get-in db [:nav/stack-state (keyword "nav.routeName" route-name)])))

(reg-sub
 :matt/matt
 (fn [db _]
   (:matt/matt db)))

(reg-sub
 :fin.stuff/asset
 (fn [db _]
   (get-in db [:fin/stuff :fin.stuff/asset])))

(reg-sub
 :get-things
 (fn [db _]
   (str (get-in db [:fin/stuff]))))
