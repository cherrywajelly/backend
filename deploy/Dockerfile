FROM yny.ocir.io/axmpikvsv3z9/jdk AS jdk
FROM yny.ocir.io/axmpikvsv3z9/jre AS jre

FROM jdk AS builder
WORKDIR /workspace
COPY gradlew .
COPY gradle gradle
COPY settings.gradle .
COPY build.gradle .
COPY src src
RUN mkdir -p src/main/resources/firebase/ && \
    cp /secret/firebase-adminsdk.json src/main/resources/firebase/
RUN chmod +x gradlew
RUN ./gradlew bootJar -x test

FROM jre AS extractor
ARG APPLICATION_FILE=timeToast.jar
WORKDIR /workspace
COPY --from=builder /workspace/build/libs/${APPLICATION_FILE} .
RUN java -Djarmode=layertools -jar ${APPLICATION_FILE} extract --destination extracted

FROM jre AS runner
WORKDIR /workspace
COPY --from=extractor /workspace/extracted/dependencies ./
COPY --from=extractor /workspace/extracted/spring-boot-loader ./
COPY --from=extractor /workspace/extracted/snapshot-dependencies ./
COPY --from=extractor /workspace/extracted/application ./
COPY build/resources/main/ BOOT-INF/classes/
CMD java org.springframework.boot.loader.launch.JarLauncher
EXPOSE 8080