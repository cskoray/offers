#
# Build stage
#
FROM eclipse-temurin:17-jdk-jammy AS build
ENV HOME=/usr/app
ENV DATASOURCE_USERNAME ${DATASOURCE_USERNAME}
ENV DATASOURCE_PASSWORD ${DATASOURCE_PASSWORD}
ENV UNSPLASH_ACCESS_KEY ${UNSPLASH_ACCESS_KEY}
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy 
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/offers.jar
EXPOSE 8080
ENTRYPOINT java -jar /app/offers.jar