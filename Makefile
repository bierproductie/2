run:
	mvn clean install

test:
	mvn clean test

stest: test sonar

sonar:
	mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar
