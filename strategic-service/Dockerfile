# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/strategic-service-1.0.jar strategic-service.jar

EXPOSE 8084

CMD java -jar strategic-service.jar