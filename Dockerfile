# base - image
FROM openjdk:11-jdk
# 빌드파일을 컨테이너로 복사
COPY target/*.jar app.jar
# jar 파일 실행
ENTRYPOINT ["java","-jar","/app.jar"]

