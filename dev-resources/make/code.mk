build: build-standard build-frontend build-backend build-lambda

build-standard:
	lein compile

build-frontend:
	lein cljsbuild once frontend

build-backend:
	lein cljsbuild once backend

build-lambda:
	lein cljsbuild once lambda

lambda-deploy:
	lein cljs-lambda deploy

clean:
	lein clean
	rm -rf pom.xml*

clean-all: clean
	rm -rf node_modules

run:
	node target/cljs/backend/aws_sandbox.js

run-fresh: clean build lambda-deploy run
