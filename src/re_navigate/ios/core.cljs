(ns re-navigate.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [re-navigate.events]
            [clojure.data :as d]
            [re-navigate.subs]))
(js* "/* @flow */")
(enable-console-print!)
(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def input (r/adapt-react-class (.-TextInput ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def react-navigation (js/require "react-navigation"))
(def add-navigation-helpers (.-addNavigationHelpers react-navigation))
(def stack-navigator (.-StackNavigator react-navigation))
(def tab-navigator (.-TabNavigator react-navigation))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn random-color
  []
  (js* "'#'+('00000'+(Math.random()*(1<<24)|0).toString(16)).slice(-6)"))

(def style
  {
   :title       {:font-size   16
                 :font-weight "100"
                 :margin      20
                 :text-align  "center"}
   :button      {:background-color "#999"
                 :padding          10
                 :margin-bottom    20
                 :border-radius    5}
   :button-text {:color       "white"
                 :text-align  "center"
                 :font-weight "bold"}
   })

(defn input-and-list-shit [props]
  (let [something (subscribe [:get-things])]
    [view {:style {:align-items      "center"
                   :justify-content  "center"
                   :flex             1
                   :background-color (random-color)}}
     [view {:style {:background-color "rgba(256,256,256,0.5)"
                    :margin-bottom    20}}
      [text {:style (style :title)} "your things: " @something]]

     [touchable-highlight {:on-press #(dispatch [:nav/reset "Index"])
                           :style    (style :button)}
      [text {:style (style :button-text)} "RESET"]]]
    ))

(defn resd [props]
  (let [number (-> props (get "params") (get "number"))
        route-name "Index"]
    [view {:style {:align-items      "center"
                   :justify-content  "center"
                   :flex             1
                   :background-color (random-color)}}
     [view {:style {:background-color "rgba(256,256,256,0.5)"
                    :margin-bottom    20}}
      [text {:style (style :title)} "fun" number]]
     [touchable-highlight
      {:style    (style :button)
       :on-press #(dispatch
                    [:nav/navigate
                     [#:nav.route {:key       (keyword (str number))
                                   :routeName :Card
                                   :params    {:number (inc number)}}
                      route-name]])}
      [text {:style (style :button-text)} "Next"]]
     [touchable-highlight {:on-press #(dispatch [:nav/reset route-name])
                           :style    (style :button)}
      [text {:style (style :button-text)} "RESET"]]]))


(def name-of-asset (reagent.ratom/atom ""))
(def price-of-asset (reagent.ratom/atom 0))

(defn matt [props]
  (let [name (-> props (get "params") (get "name"))
        my-name (subscribe [:matt/matt])
        my-asset (subscribe [:fin.stuff/asset])
        list-of-assets (subscribe [:list-assets])
        route-name "Index"]
    [view {:style {:align-items      "center"
                   :justify-content  "center"
                   :flex             1
                   :background-color (random-color)}}
     [view {:style {:background-color "rgba(256,256,256,0.5)"
                    :margin-bottom    20}}
      [text {:style (style :title)} "add asset:" @name-of-asset]]
     [input {:style {:padding-left 10
                     :font-size 16
                     :border-width 2
                     :border-color "rgba(0,0,0,0.4)"
                     :border-radius 6}
             :height 40
             :auto-correct true
             :maxLength 32
             :clear-button-mode "always"
             :value @my-name
             :placeholder @my-name
             :on-change-text (fn [value]
                               (dispatch [:update-matt value])
                               )}]
     [input {:style {:padding-left 10
                     :font-size 16
                     :border-width 2
                     :border-color "rgba(0,0,0,0.4)"
                     :border-radius 6}
             :height 40
             :auto-correct true
             :maxLength 32
             :clear-button-mode "always"
             :returnKeyType "go"
             ;; :placeholder (str @my-asset
             :on-change-text (fn [value]
                               (let [_ (println "name is:" value @name-of-asset)])
                               (reset! name-of-asset value)
                               (r/flush))}]
     [input {:style {:padding-left 10
                     :font-size 16
                     :border-width 2
                     :border-color "rgba(0,0,0,0.4)"
                     :border-radius 6}
             :height 40
             :auto-correct true
             :maxLength 32
             :clear-button-mode "always"
             :returnKeyType "go"
             ;; :placeholder (str @my-asset
             :on-change-text (fn [value]
                               (let [_ (println "price is" value @price-of-asset)])
                               (reset! price-of-asset value)
                               (r/flush))}]



     [touchable-highlight {:on-press #(dispatch [:nav/reset route-name])
                           :style    (style :button)}
      [text {:style (style :button-text)} "RESET"]]
     [touchable-highlight {:on-press #(dispatch [:add-asset {:fin.stuff/name @name-of-asset :fin.stuff/asset @price-of-asset}])
                           :style    (style :button)}
      [text {:style (style :button-text)} "add to db"]]
     [view [text "having fun" @list-of-assets]]]))

(defn settings []
  [view {:style {:flex 1
                 :justify-content "center"
                 :align-items "center"}}
   [text "SETTINGS"]])



(defn app-root [{:keys [navigation]}]
  [view {:style {:flex-direction   "column"
                 :flex             1
                 :padding          40
                 :align-items      "center"
                 :background-color (random-color)}}
   [text {:style (style :title)} "placeholder"]
   [image {:source logo-img
           :style  {:width 80 :height 80 :margin-bottom 30}}]
   [touchable-highlight {:style    (style :button)
                         :on-press #(dispatch
                                      [:nav/navigate
                                       [#:nav.route {:key       :0
                                                     :routeName :Card
                                                     :params    {:number 1}}
                                        "Index"]])}
    [text {:style (style :button-text)} "press me"]]
   [touchable-highlight {:style    (style :button)
                         :on-press #(dispatch
                                     [:nav/navigate
                                      [#:nav.route {:key       :0
                                                    :routeName :Settings
                                                    :params    {:number 1}}
                                       "Index"]])}
    [text {:style (style :button-text)} "settings"]]
   [touchable-highlight {:style    (style :button)
                         :on-press #(dispatch
                                     [:nav/navigate
                                      [#:nav.route {:key       :0
                                                    :routeName :Matt
                                                    :params    {:name "m"}}
                                       "Index"]])}
    [text {:style (style :button-text)} "assets"]]
   [touchable-highlight {:style    (style :button)
                         :on-press #(dispatch
                                     [:nav/navigate
                                      [#:nav.route {:key       :0
                                                    :routeName :InputAndListShit
                                                    :params    {:name "m"}}
                                       "Index"]])}
    [text {:style (style :button-text)} "input-list-and-shit"]]

   ])


(defn nav-wrapper [component title]
  (let [comp (r/reactify-component
               (fn [{:keys [navigation]}]
                 [component (-> navigation .-state js->clj)]))]
    (aset comp "navigationOptions" #js {"title" title})
    comp))


(def resd-comp (nav-wrapper resd #(str "Card "
                                       (aget % "state" "params" "number"))))

(def input-and-list-shit-comp (nav-wrapper input-and-list-shit #(str "Card "
                                       (aget % "state" "params" "number"))))

(def matt-comp (nav-wrapper matt #(str "Inserting assets screen "
                                       (aget % "state" "params" "number"))))

(def settings-comp (nav-wrapper settings #(str "The Settings ")))

(def app-root-comp (nav-wrapper app-root "Welcome"))

(def stack-router {:Home {:screen app-root-comp}
                   :Card {:screen resd-comp}
                   :InputAndListShit {:screen input-and-list-shit-comp}
                   :Matt {:screen matt-comp}
                   :Settings {:screen settings-comp}})


(def sn (r/adapt-react-class (stack-navigator (clj->js stack-router))))

(defn card-start [] (let [nav-state (subscribe [:nav/stack-state "Index"])]
                      (fn []
                        (js/console.log @nav-state)
                        [sn {:navigation (add-navigation-helpers
                                           (clj->js
                                             {"dispatch" #(do
                                                            (js/console.log "EVENT" %)
                                                            (dispatch [:nav/js [% "Index"]]))
                                              "state"    (clj->js @nav-state)}))}])))

(def tab-router {:Index    {:screen (nav-wrapper card-start "Index")}
                 :Settings {:screen (nav-wrapper settings "Settings")}})



(defn tab-navigator-inst []
  (tab-navigator
    (clj->js tab-router)
    (clj->js {:order ["Index" "Settings" #_"InputAndListShit" #_"Matt"]
              :initialRouteName "Index"})))

(defn get-state [action]
  (-> (tab-navigator-inst)
      .-router
      (.getStateForAction action)))

(defonce tn
  (let [tni (tab-navigator-inst)]
    (aset tni "router" "getStateForAction" #(let [new-state (get-state %)]
                                              (js/console.log "STATE" % new-state)
                                              (dispatch [:nav/set new-state])
                                              new-state) #_(do (js/console.log %)
                                                               #_(get-state %)))
    (r/adapt-react-class tni)))

(defn start []
  (let [nav-state (subscribe [:nav/tab-state])]
    (fn []
      [tn])))

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent app-registry "ReNavigate" #(r/reactify-component start)))
