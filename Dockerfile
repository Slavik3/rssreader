FROM java:8-jdk-alpine

COPY ./target/rssreader-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch rssreader-0.0.1-SNAPSHOT.jar'

ARG PORT=8080

EXPOSE ${PORT}

ENTRYPOINT ["java","-jar","rssreader-0.0.1-SNAPSHOT.jar"]

#EXPOSE 8080