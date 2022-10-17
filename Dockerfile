# base - image
FROM openjdk:11-jdk
# 변수설정 (빌드 파일의 경로)
ARG JAR_FILE=build/libs/*.jar
# 빌드파일을 컨테이너로 복사
COPY ${JAR_FILE} app.jar
# jar 파일 실행
ENTRYPOINT ["java","-jar","/app.jar"]