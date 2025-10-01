# Multi-stage build를 사용하여 최적화된 이미지 생성
FROM eclipse-temurin:17-jdk AS build

# Maven 설치
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# 작업 디렉토리 설정
WORKDIR /app

# Maven 설정 파일 복사
COPY pom.xml .

# 의존성 다운로드 (캐시 최적화)
RUN mvn dependency:go-offline -B

# 소스 코드 복사
COPY src ./src

# 애플리케이션 빌드
RUN mvn clean package -DskipTests

# 실행 단계
FROM eclipse-temurin:17-jre

# 작업 디렉토리 설정
WORKDIR /app

# 업로드 디렉토리 생성
RUN mkdir -p /app/uploads

# 빌드된 JAR 파일 복사
COPY --from=build /app/target/pt-project-back-1.0.0.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
