(ns sandbox.backend.util
  (:require [cljs.core.async :as async]
            [cljs.reader]
            [clojure.string :as string]
            [eulalie.instance-data :as instance-data]
            [eulalie.lambda.util :as lambda]
            [taoensso.timbre :as log])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn now []
  (.getTime (js/Date.)))

(defn sighting-in [msg]
  (if (string? msg)
    (-> msg
        cljs.reader/read-string
        (assoc :timestamp (now)))
    (do
      (log/errorf "Can't get sighting data from non-string message: %s" msg)
      {})))

(def sighting-out pr-str)

(defn queue-name! [{:keys [port]}]
  (go
    (let [{:keys [instance-id region]}
          (async/<! (instance-data/instance-identity!
                      :document {:parse-json true}))]
      (string/join "_" [region instance-id port]))))

(defn topic-to-queue! [{:keys [topic-name creds] :as config}]
  (log/debug "topic-name:" topic-name)
  (log/debug "creds:" creds)
  (log/debug "config:" config)
  (go
    (let [queue-name (async/<! (queue-name! config))]
      (-> (lambda/request!
            creds
            :topic-to-queue {
              :topic-name topic-name
              :queue-name queue-name})
          async/<!
          second))))

(defn channel-websocket! [ws to-client from-client]
  (.on ws "message" (fn [m _]
                      (async/put! from-client m)))
  (.on ws "close" (fn []
                    (async/close! from-client)
                    (async/close! to-client)))
  (go
    (loop []
      (when-let [value (<! to-client)]
        (.send ws value)
        (recur))))
  [from-client to-client])

(defn conj+evict [q item limit]
  (-> q
      (cond-> (= limit (count q)) pop)
      (conj item)))
