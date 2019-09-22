# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/telegram-1.0.jar telegram.jar

EXPOSE 8083

CMD java -jar telegram.jar