FROM openjdk:11-jdk-slim AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:11-jdk-slim
COPY --from=builder build/libs/*.jar app.jar
# builder 이미지에서 build/libs/*.jar 파일을 app.jar로 복사
EXPOSE 8080
# 컨테이너 Port를 8080로 노출
ENTRYPOINT ["java","-jar","/app.jar"]
# jar 파일 실행


#cli 로 도커 이미지 생성방법
# docker build -t rest/spring-docker .
#cli 로 실행 8080포트 , 실행후 localhost:8080 으로 확인
# docker run -d -p 8080:8080 rest/spring-docker .