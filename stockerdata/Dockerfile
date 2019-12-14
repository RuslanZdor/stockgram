# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/stocker-data-1.0.jar data-service.jar

EXPOSE 8080

CMD java -jar data-service.jar