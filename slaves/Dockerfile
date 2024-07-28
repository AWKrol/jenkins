FROM jenkins/slave:latest-jdk11

USER root

ARG MAVEN_VERSION

ENV MAVEN_HOME="/opt/maven"
ENV MAVEN_CONFIG_HOME="/home/jenkins/.m2"

RUN mkdir -p $MAVEN_HOME

RUN curl -LSso /var/tmp/apache-maven-$MAVEN_VERSION-bin.tar.gz https://apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz
RUN tar xzvf /var/tmp/apache-maven-$MAVEN_VERSION-bin.tar.gz -C $MAVEN_HOME --strip-components=1
RUN rm /var/tmp/apache-maven-$MAVEN_VERSION-bin.tar.gz
RUN update-alternatives --install /usr/bin/mvn mvn /opt/maven/bin/mvn 10000
RUN mkdir -p $MAVEN_CONFIG_HOME