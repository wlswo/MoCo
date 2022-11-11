# base - image
FROM openjdk:11-jdk AS builder
# gradle 복사
COPY gradlew .
# gradle 복사
COPY gradle gradle
# build.gradle 복사
COPY build.gradle .
# setting.gradle 복사
COPY settings.gradle .
# 소스 복사
COPY src src
# 실행 권한 부여
RUN chmod +x ./gradlew
# gradlew를 사용하여 실행 가능한 jar 파일 생성
RUN ./gradlew bootJar
# jar 파일 경로
ARG JAR_FILE=build/libs/*.jar

# 베이스 이미지
FROM openjdk:11-jdk
# builder 이미지에서 build/libs/*.jar 파일을 app.jar로 복사
COPY --from=builder build/libs/*.jar app.jar
# jar 파일 실행
ENTRYPOINT ["java","-jar","/app.jar"]

