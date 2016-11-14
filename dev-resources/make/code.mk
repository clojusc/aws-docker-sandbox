build:
	lein compile
	lein cljsbuild once frontend
	lein cljsbuild once backend
	lein cljsbuild once lambda

lambda-deploy:
	lein cljs-lambda deploy

clean:
	lein clean
	rm -rf pom.xml* node_modules

run:
	node target/cljs/backend/chemtrack.js

run-fresh: clean build lambda-deploy run
