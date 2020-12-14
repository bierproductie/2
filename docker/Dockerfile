FROM maven:3.6.3-openjdk-14


WORKDIR /usr/src/app

COPY . /usr/src/app

RUN mvn clean install

ENTRYPOINT ["./docker/entrypoint.sh"]
