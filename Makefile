IMAGE=registry.nymann.dev/bierproductie/opc_ua_client
ONBUILD=registry.nymann.dev/bierproductie/opc_ua_client:onbuild

build:
	docker build --cache-from ${ONBUILD} -t ${ONBUILD} -f docker/Dockerfile .
	docker push ${IMAGE}

run:
	mvn clean install

test:
	mvn clean test

sonar:
	mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar

stest: test sonar
