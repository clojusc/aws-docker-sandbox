(defproject clojusc/aws-docker-sandbox "0.1.0-SNAPSHOT"
  :description "Clojure(script) App for Sandboxing Docker on AWS"
  :url "https://github.com/clojusc/aws-docker-sandbox"
  :license
    {:name "Apache License, Version 2.0"
     :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies
    [[org.clojure/clojure "1.8.0"]
     [org.clojure/core.async "0.2.395"]
     [org.clojure/clojurescript "1.9.293"]
     [clojusc/env-ini "0.2.0-SNAPSHOT"]
     [clojusc/cljs-tools "0.1.0-SNAPSHOT"]
     [cljsjs/react "15.3.1-1"]
     [reagent "0.6.0"]
     [reagent-forms "0.5.28"]
     [io.nervous/fink-nottle "0.4.6"]
     [io.nervous/cljs-lambda "0.3.2"]
     [io.nervous/cljs-nodejs-externs "0.2.0"]
     [jarohen/chord "0.7.0"]
     [com.taoensso/timbre "4.7.4"]
     [cljsjs/moment "2.15.2-3"]]
  :exclusions [[org.clojure/clojure]]
  :source-paths ["src/cljc"]
  :npm
    {:dependencies
      [[source-map-support "0.2.8"]
       [express "4.13.1"]
       [express-ws "0.2.6"]
       [colors "1.1.2"]]}
  :resource-paths ["resources"]
  :clean-targets ^{:protect false}
    ["resources/public/js/out"
     "resources/public/js/aws_sandbox.js"
     "target"]
  :plugins
    [[lein-cljsbuild "1.1.4"]
     [lein-npm "0.6.2"]
     [io.nervous/lein-cljs-lambda "0.6.2"]]
  :cljs-lambda
    {:cljs-build-id "lambda"
     :defaults
       {:role "arn:aws:iam::715812439605:role/lambda-test"}
     :functions [
       {:name "topic-to-queue"
        :invoke sandbox.lambda/topic-to-queue
        :timeout 20}]}
  :cljsbuild
    {:builds [
      {:id "backend"
       :source-paths ["src/cljs/backend"]
       :compiler {
         :output-to "target/cljs/backend/aws_sandbox.js"
         :output-dir "target/cljs/backend"
         :optimizations :none
         :main "sandbox.backend"
         :target :nodejs}}
      {:id "frontend"
       :source-paths ["src/cljs/frontend"]
       :figwheel true
       :compiler {
         :asset-path "js/out"
         :output-to "resources/public/js/aws_sandbox.js"
         :output-dir "resources/public/js/out"
         :optimizations :advanced}}
      {:id "lambda"
       :source-paths ["src/cljs/lambda"]
       :compiler
         {:output-to "target/cljs/lambda/aws_sandbox.js"
          :output-dir "target/cljs/lambda"
          :optimizations :advanced
          :target :nodejs}}]}
  :profiles
    {:dev
      {:source-paths [
         "src/cljs/frontend"
         "src/cljs/backend"
         "src/cljs/lambda"]
       :repl-options
        {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
          :dependencies
            [[com.cemerick/piggieback "0.2.1"]
             [org.clojure/tools.nrepl "0.2.12"]]}})
