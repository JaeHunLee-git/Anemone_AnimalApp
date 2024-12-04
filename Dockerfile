# 베이스 이미지 설정 - OpenJDK 17을 사용
FROM openjdk:17-jdk-slim

# JAR 파일을 컨테이너로 복사
ARG JAR_FILE=build/libs/sivillage-backend-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-spring.jar

# 컨테이너가 시작될 때 애플리케이션을 실행
ENTRYPOINT ["java","-jar","/docker-spring.jar"]