build:
	lein compile
	lein cljsbuild once

lambda-deploy:
	lein cljs-lambda deploy

clean:
	rm -rf pom.xml* target

run:
	node target/cljs/backend/chemtrack.js
