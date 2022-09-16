FROM ubuntu:22.04
ENV LANG=en_US.UTF-8
ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update && \
  apt-get upgrade -y && \
  apt-get install -q -y openjdk-8-jdk

WORKDIR /java/dosya
WORKDIR /java
COPY target/search.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]

