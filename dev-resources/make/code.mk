build:
	lein compile
	lein cljsbuild once

clean:
	rm -rf pom.xml* target
