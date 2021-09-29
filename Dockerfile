#FROM openjdk:11-jdk as builder
#WORKDIR application
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} application.jar
#RUN java -Djarmode=layertools -jar application.jar extract
#FROM adoptopenjdk:11-jre-hotspot
#WORKDIR application
#COPY --from=builder application/dependencies/ ./
#COPY --from=builder application/snapshot-dependencies/ ./
#COPY --from=builder application/resources/ ./
#COPY --from=builder application/application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
#

FROM openjdk:11-jre-slim

WORKDIR /root
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.demo.DemoApplication"]


FROM openjdk:11-jre-slim
EXPOSE 8081
ARG JAR_FILE=build/libs/restaurant-reservation-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

RUN java -Djarmode=layertools -jar app.jar extract
FROM adoptopenjdk:11-jre-hotspot

ENTRYPOINT ["java","-jar","/app.jar"]



#알파인 리눅스 설치 -> jar 실행 (로그 경로 등을 마운트 시키는 형태)
