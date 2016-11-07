# aws-docker-sandbox

[![Build Status][travis-badge]][travis]
[![Dependencies Status][deps-badge]][deps]
[![Clojars Project][clojars-badge]][clojars]
[![Clojure version][clojure-v]](project.clj)

*Clojure(script) App for Sandboxing Docker on AWS*

[![Project Logo][logo-large]][logo]


#### Contents

* [About](#about-)
* [AWS Services](#aws-services-)
* [Dependencies](#dependencies-)
* [Setup](#setup-)
* [License](#license-)


## About [&#x219F;](#contents)

This is a Clojure and Clojurescript application for running Docker containers
(and managing related workflows) for complicated science models and services.
It takes advantage of AWS infrastructure and services in order to massively
reduce the spin-up time for researches that would otherwise have to provide
these themselves, thus allowing them to get to the science immediately.

Initial code was based on work done at [Nervous.io][nervous] by
[Moe Aboulkheir][moe]. (See the related [blog post][orig blog post].)


## AWS Services [&#x219F;](#contents)

This application makes use of the following Amazon Web Services:

* [EC2][ec2]
* [ECS][ecs] / [ECR][ecr]
* [SQS][sqs]
* [Lambda][lambda]


## Dependencies [&#x219F;](#contents)
  - NPM
  - [AWS CLI][aws cli]
  - EC2 (The queue naming code retrieves instance metadata.
   [Easy enough to change][queue naming code])


## Setup [&#x219F;](#contents)

Steps:
 - `lein deps`
 - [Insert valid IAM role name in `project.clj`][cljs-lambda]
   (`lein cljs-lambda default-iam-role` will allow deployment,
   but SNS & SQS permissions must be added for execution)
 - `lein cljs-lambda deploy`
 - `lein cljsbuild once`
 - `node target/backend/chemtrack.js`


## License [&#x219F;](#contents)

Copyright © 2015, Nervous.io

Copyright © 2016, Clojure-Aided Enrichment Center

Apache License, Version 2.0.


<!-- Named page links below: /-->

[travis]: https://travis-ci.org/clojusc/aws-docker-sandbox
[travis-badge]: https://travis-ci.org/clojusc/aws-docker-sandbox.png?branch=master
[deps]: http://jarkeeper.com/clojusc/aws-docker-sandbox
[deps-badge]: http://jarkeeper.com/clojusc/aws-docker-sandbox/status.svg
[logo]: resources/images/aws-docker-sandbox-small.png
[logo-large]: resources/images/aws-docker-sandbox-medium.png
[tag-badge]: https://img.shields.io/github/tag/clojusc/aws-docker-sandbox.svg?maxAge=2592000
[tag]: https://github.com/clojusc/aws-docker-sandbox/tags
[clojure-v]: https://img.shields.io/badge/clojure-1.8.0-blue.svg
[clojars]: https://clojars.org/clojusc/aws-docker-sandbox
[clojars-badge]: https://img.shields.io/clojars/v/clojusc/aws-docker-sandbox.svg
[nervous]: https://github.com/nervous-systems
[moe]: https://github.com/moea
[orig blog post]: https://nervous.io/clojure/clojurescript/node/aws/2015/08/09/chemtrails/
[ec2]: http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/concepts.html
[ecs]: http://docs.aws.amazon.com/AmazonECS/latest/developerguide/Welcome.html
[ecr]: http://docs.aws.amazon.com/AmazonECR/latest/userguide/Registries.html
[sqs]: http://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/Welcome.html
[lambda]: https://docs.aws.amazon.com/lambda/latest/dg/welcome.html
[aws cli]: https://aws.amazon.com/cli/
[queue naming code]: https://github.com/nervous-systems/chemtrack-example/blob/master/backend/chemtrack/backend/util.cljs#L23
[cljs-lambda]: https://github.com/nervous-systems/cljs-lambda
