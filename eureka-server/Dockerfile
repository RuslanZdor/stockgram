# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/eureka-server-1.0.jar eureka-server.jar

EXPOSE 8761:8761

CMD java -jar eureka-server.jar