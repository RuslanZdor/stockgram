# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

# Install Chrome for Selenium
curl https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb -o /chrome.deb
dpkg -i /chrome.deb || apt-get install -yf
rm /chrome.deb

# Install chromedriver for Selenium
curl https://chromedriver.storage.googleapis.com/2.31/chromedriver_linux64.zip -o /usr/local/bin/chromedriver
chmod +x /usr/local/bin/chromedriver

ADD ./target/telegram-1.0.jar telegram.jar

EXPOSE 8083

CMD java -jar telegram.jar