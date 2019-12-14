# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install -y unzip openjdk-8-jre-headless xvfb libxi6 libgconf-2-4 && \
# Install curl
apt-get install curl -y && \
apt-get install wget -y && \
apt-get install unzip -y && \
apt-get install gnupg -y && \
apt-get install xvfb -y && \
apt-get install -y apt-transport-https

# Versions
#ENV CHROME_DRIVER_VERSION="curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE"
ENV CHROME_DRIVER_VERSION=77.0.3865.40
ENV SELENIUM_STANDALONE_VERSION=3.4.0
ENV SELENIUM_SUBDIR=3.4

# Install dependencies.
RUN apt-get install -y unzip openjdk-8-jre-headless xvfb libxi6 libgconf-2-4

# Install Chrome.
RUN curl -sS -o - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add
RUN echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list
RUN apt-get -y update
RUN apt-get -y install google-chrome-stable

# Install ChromeDriver.
RUN curl https://chromedriver.storage.googleapis.com/${CHROME_DRIVER_VERSION}/chromedriver_linux64.zip -o chromedriver_linux64.zip
#                                                                      77.0.3865.40/chromedriver_linux64.zip
RUN unzip chromedriver_linux64.zip -d /
RUN rm chromedriver_linux64.zip
RUN mv -f chromedriver /usr/local/bin/chromedriver
RUN chown root:root /usr/local/bin/chromedriver
RUN chmod 0755 /usr/local/bin/chromedriver

# Install Selenium.
RUN curl http://selenium-release.storage.googleapis.com/${SELENIUM_SUBDIR}/selenium-server-standalone-${SELENIUM_STANDALONE_VERSION}.jar -o selenium-server-standalone-${SELENIUM_STANDALONE_VERSION}.jar
RUN mv -f selenium-server-standalone-${SELENIUM_STANDALONE_VERSION}.jar /usr/local/bin/selenium-server-standalone.jar
RUN chown root:root /usr/local/bin/selenium-server-standalone.jar
RUN chmod 0755 /usr/local/bin/selenium-server-standalone.jar

EXPOSE 8083

ADD ./target/telegram-1.0.jar telegram.jar
CMD java -jar telegram.jar