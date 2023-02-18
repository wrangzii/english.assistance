#Build stage

FROM gradle:latest AS BUILD
WORKDIR /usr/app
COPY . .
RUN gradle build
# Package stage

FROM adoptopenjdk/openjdk11:alpine-jre
ENV JAR_NAME=app-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
EXPOSE 8080
ENTRYPOINT exec java -jar $APP_HOME/app/build/libs/$JAR_NAME
