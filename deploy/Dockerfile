FROM eclipse-temurin:17.0.12_7-jdk-jammy AS jdk
FROM eclipse-temurin:17.0.12_7-jre-jammy AS jre

FROM jdk AS builder
WORKDIR /workspace
COPY gradlew .
COPY gradle gradle
COPY settings.gradle .
COPY build.gradle .
COPY src src
RUN chmod +x gradlew
RUN ./gradlew bootJar

FROM jre AS extractor
ARG APPLICATION_FILE=timeToast.jar
WORKDIR /workspace
COPY --from=builder /workspace/build/libs/${APPLICATION_FILE} .
RUN java -Djarmode=tools -jar ${APPLICATION_FILE} extract --layers --destination extracted

FROM jre AS runner
ENV APPLICATION_FILE=timeToast.jar
WORKDIR /workspace
COPY --from=extractor /workspace/extracted/dependencies ./
COPY --from=extractor /workspace/extracted/spring-boot-loader ./
COPY --from=extractor /workspace/extracted/snapshot-dependencies ./
COPY --from=extractor /workspace/extracted/application ./
CMD java -jar ${APPLICATION_FILE}
EXPOSE 8080
