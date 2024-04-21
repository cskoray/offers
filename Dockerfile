FROM openjdk:17-jdk-slim AS builder
ADD . /src
WORKDIR /src
RUN mvn clean package

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=builder /src/service/build/libs/offers-*.jar /usr/local/bin/offers.jar
HEALTHCHECK --retries=12 --interval=10s CMD curl -s localhost:8080/health || exit 1
RUN chmod +x /usr/local/bin/offers.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/offers.jar"]

VOLUME /usr/local/offers
