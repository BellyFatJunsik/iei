# PT Project Backend - Docker 실행 가이드

Docker를 사용하여 PT Project 백엔드를 쉽게 실행할 수 있습니다.

## 🚀 빠른 시작

### 1. Docker 환경 실행
```bash
# 실행 스크립트 사용 (권장)
./docker-start.sh

# 또는 직접 실행
docker-compose up -d
```

### 2. 서비스 확인
- **백엔드 API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **phpMyAdmin**: http://localhost:8081

## 📋 서비스 구성

### MySQL 데이터베이스
- **포트**: 3306
- **데이터베이스**: pt_project
- **사용자명**: ptuser
- **비밀번호**: ptpass123
- **루트 비밀번호**: ptproject123

### 백엔드 애플리케이션
- **포트**: 8080
- **Java 17** 기반 Spring Boot 애플리케이션
- **JWT 인증** 시스템
- **Swagger API 문서** 포함

### phpMyAdmin
- **포트**: 8081
- **웹 기반 MySQL 관리 도구**

## 🛠️ 개발 환경 설정

### 개발 모드로 실행
```bash
# 개발 환경 설정으로 실행
docker-compose -f docker-compose.yml -f docker-compose.override.yml up -d
```

### 로그 확인
```bash
# 전체 로그
docker-compose logs -f

# 특정 서비스 로그
docker-compose logs -f backend
docker-compose logs -f mysql
```

### 컨테이너 접속
```bash
# 백엔드 컨테이너 접속
docker exec -it pt-project-backend bash

# MySQL 컨테이너 접속
docker exec -it pt-project-mysql mysql -u ptuser -p pt_project
```

## 🔧 관리 명령어

### 서비스 중지
```bash
docker-compose down
```

### 서비스 재시작
```bash
docker-compose restart
```

### 볼륨 삭제 (데이터 초기화)
```bash
docker-compose down -v
```

### 이미지 재빌드
```bash
docker-compose build --no-cache
```

## 📁 디렉토리 구조

```
pt-project-back/
├── docker-compose.yml          # 기본 Docker Compose 설정
├── docker-compose.override.yml # 개발 환경 오버라이드
├── Dockerfile                  # 백엔드 애플리케이션 이미지
├── .dockerignore              # Docker 빌드 제외 파일
├── docker-start.sh            # 실행 스크립트
├── database_schema.sql        # 데이터베이스 스키마
├── mysql_config/              # MySQL 설정 파일
│   └── my.cnf
└── uploads/                   # 파일 업로드 디렉토리
```

## 🐛 문제 해결

### 포트 충돌
```bash
# 포트 사용 중인 프로세스 확인
lsof -i :8080
lsof -i :3306
lsof -i :8081

# 프로세스 종료
kill -9 <PID>
```

### 데이터베이스 연결 오류
```bash
# MySQL 컨테이너 상태 확인
docker-compose ps mysql

# MySQL 로그 확인
docker-compose logs mysql

# MySQL 재시작
docker-compose restart mysql
```

### 백엔드 애플리케이션 오류
```bash
# 백엔드 로그 확인
docker-compose logs backend

# 백엔드 재시작
docker-compose restart backend
```

## 🔐 보안 설정

### 프로덕션 환경
프로덕션 환경에서는 다음 설정을 변경하세요:

1. **데이터베이스 비밀번호** 변경
2. **JWT 시크릿 키** 변경
3. **포트 노출** 제한
4. **방화벽** 설정

### 환경 변수 설정
```bash
# .env 파일 생성
echo "MYSQL_ROOT_PASSWORD=your_secure_password" > .env
echo "MYSQL_PASSWORD=your_secure_password" >> .env
echo "JWT_SECRET=your_jwt_secret_key" >> .env
```

## 📊 모니터링

### 리소스 사용량 확인
```bash
# 컨테이너 리소스 사용량
docker stats

# 디스크 사용량
docker system df
```

### 헬스 체크
```bash
# 서비스 상태 확인
docker-compose ps

# 백엔드 헬스 체크
curl http://localhost:8080/actuator/health
```

## 🚀 배포

### 프로덕션 배포
```bash
# 프로덕션 환경 변수 설정
export MYSQL_ROOT_PASSWORD=your_production_password
export MYSQL_PASSWORD=your_production_password
export JWT_SECRET=your_production_jwt_secret

# 서비스 시작
docker-compose up -d
```

이제 Docker를 사용하여 PT Project 백엔드를 쉽게 실행하고 관리할 수 있습니다! 🎉
