# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/yahoo-service-1.0.jar yahoo-service.jar

EXPOSE 8081

CMD java -jar yahoo-service.jar