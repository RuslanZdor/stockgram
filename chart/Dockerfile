# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/stocker-graph-1.0.war stocker-graph.war

EXPOSE 8082

CMD java -jar stocker-graph.war