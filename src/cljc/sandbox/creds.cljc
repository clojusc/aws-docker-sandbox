(ns sandbox.creds
  (:require [clojusc.env-ini.core :as env-ini]
            [eulalie.creds])
  (:refer-clojure :exclude [load]))

(defn profile->kwd
  ""
  [profile]
  (if (keyword? profile)
    profile
    (keyword profile)))

(defn load-config
  ""
  []
  (merge
    (env-ini/load-data "~/.aws/config")
    (env-ini/load-data "~/.aws/credentials")))

(defn get-region
  ""
  [cfg profile fallback]
  (let [region (env-ini/get cfg :aws-default-region profile :region)]
    (if region
      {:region region}
      fallback)))

(defn get-output
  ""
  [cfg profile fallback]
  (let [output (env-ini/get cfg :aws-default-output profile :output)]
    (if output
      {:output output}
      fallback)))

(defn load
  ""
  ([]
    (load :default))
  ([profile-name]
    (let [profile (profile->kwd profile-name)
          cfg (load-config)]
      (merge
        (eulalie.creds/env)
        {:access-key (env-ini/get
                       cfg :aws-access-key-id
                       profile :aws-access-key-id)
         :secret-key (env-ini/get
                       cfg :aws-secret-access-key
                       profile :aws-secret-access-key)}
        (get-region cfg profile {})
        (get-output cfg profile {})))))
