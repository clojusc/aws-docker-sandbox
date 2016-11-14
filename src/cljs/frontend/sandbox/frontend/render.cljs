(ns sandbox.frontend.render
  (:require [sandbox.frontend.template :as t]
            [cljs.core.async :as async]
            [clojure.string :as str]
            [cljsjs.moment]))

(defn elt [template [sym readable]]
  (t/substitute
   {:workflow/value sym :workflow/name readable}
   template))

(defn form [sighting {:keys [elements sightings-out]}]
  (t/substitute
   {:workflow/elements (map (partial elt t/form-element) elements)
    :workflow/handler  (fn [& _]
                     (async/put! sightings-out @sighting))}
   t/form))

(defn sighting [{:keys [timestamp city elements]}]
  (t/substitute
   {:workflow/city city
    :workflow/timestamp timestamp
    :workflow.timestamp/formatted
    (-> timestamp
        js/moment
        (.format "YYYY-MM-DD HH:mm"))
    :workflow/elements (map (fn [sym]
                          (elt t/sighting-element
                               [sym (-> sym name str/capitalize)]))
                        elements)}
   t/sighting))

(defn app [form-renderer {:keys [recent] :as deps}]
  (t/substitute
   {:workflow/sightings (map sighting (reverse @recent))
    :workflow/form      (form-renderer deps)}
   t/app))
