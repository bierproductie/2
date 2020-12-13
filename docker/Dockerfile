FROM maven:3.6.3-openjdk-14


WORKDIR /usr/src/app

COPY . /usr/src/app

RUN mvn clean install

CMD ["java", "-jar", "target/opc_ua_client-0.0.1.jar"]
