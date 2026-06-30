# 1. 빌드 단계 (Gradle + JDK 21)
FROM gradle:8.10-jdk21 AS build
WORKDIR /app
#의존성 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
RUN ./gradlew dependencies --no-daemon || true
#소스 코드 복사 및 JAR 빌드
COPY src ./src
RUN chmod +x gradlew
RUN ./gradlew clean bootJar -x test --no-daemon
# 2. 실행 단계 (JRE 21)
FROM eclipse-temurin:21-jre
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]