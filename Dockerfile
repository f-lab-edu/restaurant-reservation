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

# 상황에 맞게 실행하도록 추가 (dev, prod)
ARG ENVIRONMENT
ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
# jar 파일 실행


#cli 로 도커 이미지 생성방법
# docker build -t rest/spring-docker .
#cli 로 실행 8080포트 , 실행후 localhost:8080 으로 확인
# docker run -d -p 8080:8080 rest/spring-docker .
# 실행하는 상태에 따라 다르게 실행가능 -현재 dev
# docker build --build-arg ENVIRONMENT=dev -t rest-dev:0.0.1 .