# PT Project Backend

물리치료 원격재활 플랫폼의 백엔드 API 서버입니다.

## 기술 스택

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT 인증)
- **MyBatis 3.0.3**
- **MySQL 8.0**
- **HikariCP** (Connection Pool)
- **Swagger/OpenAPI 3** (API 문서화)

## 주요 기능

### 1. 회원 관리
- 환자/물리치료사/관리자 회원가입 및 로그인
- JWT 기반 인증/인가
- 프로필 관리 및 이미지 업로드
- 물리치료사 등록 승인 시스템

### 2. 일정 관리 (Calendar)
- 원격재활 및 방문 일정 관리
- 캘린더 기반 일정 조회
- 시간 충돌 방지
- 일정 상태 관리 (예약, 진행중, 완료, 취소)

### 3. 운동 프로그램 (Program)
- AI 분석 기반 운동 루틴 추천
- 프로그램 등록/수정/삭제
- 영상 및 썸네일 관리
- 프로그램 완료 기록 및 통계

### 4. 후기 시스템 (Review)
- 프로그램 후기 작성/수정/삭제
- 평점 시스템 (1-5점)
- 댓글 기능

### 5. 통계 및 분석 (Stats)
- 관리자 대시보드
- 환자별 진행률 통계
- 치료사별 성과 분석
- 월별 사용자/수익 통계

### 6. 공지사항 (Notice)
- 공지사항 등록/수정/삭제
- 카테고리별 분류
- 우선순위 설정

## 프로젝트 구조

```
src/main/java/kr/co/iei/
├── Application.java                 # 메인 실행 클래스
├── config/                         # 설정 클래스
│   ├── WebConfig.java              # CORS, 인터셉터 설정
│   ├── SecurityConfig.java         # JWT + 시큐리티 설정
│   ├── SwaggerConfig.java          # API 문서 설정
│   └── DatabaseConfig.java         # DB 설정
├── common/                         # 공통 유틸리티
│   ├── dto/PageInfo.java           # 페이징 정보
│   ├── util/                       # 유틸리티 클래스
│   ├── exception/GlobalExceptionHandler.java  # 글로벌 예외 처리
│   └── response/ApiResponse.java   # API 공통 응답 포맷
├── member/                         # 회원 도메인
├── admin/                          # 관리자 도메인
├── calendar/                       # 일정 관리 도메인
├── program/                        # 운동 프로그램 도메인
├── review/                         # 후기 도메인
├── stats/                          # 통계 도메인
└── notice/                         # 공지사항 도메인
```

## 설치 및 실행

### 1. 사전 요구사항
- Java 17 이상
- MySQL 8.0 이상
- Maven 3.6 이상

### 2. 데이터베이스 설정
```sql
-- MySQL에서 데이터베이스 생성
mysql -u root -p < database_schema.sql
```

### 3. 애플리케이션 설정
`src/main/resources/application.properties` 파일에서 데이터베이스 연결 정보를 수정하세요:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pt_project?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. 애플리케이션 실행
```bash
# Maven으로 실행
mvn spring-boot:run

# 또는 JAR 파일로 실행
mvn clean package
java -jar target/pt-project-back-1.0.0.jar
```

### 5. API 문서 확인
애플리케이션 실행 후 다음 URL에서 Swagger UI를 확인할 수 있습니다:
- http://localhost:8080/swagger-ui.html

## API 엔드포인트

### 인증 관련
- `POST /api/auth/login` - 로그인
- `POST /api/auth/register` - 회원가입
- `GET /api/auth/profile` - 프로필 조회
- `PUT /api/auth/profile` - 프로필 수정

### 일정 관리
- `GET /api/calendar` - 일정 목록 조회
- `POST /api/calendar` - 일정 등록
- `PUT /api/calendar/{id}` - 일정 수정
- `DELETE /api/calendar/{id}` - 일정 삭제

### 운동 프로그램
- `GET /api/program` - 프로그램 목록 조회
- `POST /api/program` - 프로그램 등록
- `GET /api/program/{id}` - 프로그램 상세 조회
- `PUT /api/program/{id}` - 프로그램 수정

### 관리자 기능
- `GET /api/admin/dashboard` - 대시보드 통계
- `GET /api/admin/stats/**` - 각종 통계 조회

## 보안 설정

- JWT 토큰 기반 인증
- 역할 기반 접근 제어 (RBAC)
- CORS 설정
- SQL Injection 방지 (MyBatis Parameter Binding)
- XSS 방지 (입력값 검증)

## 개발 가이드

### 1. 새로운 도메인 추가
1. `dto/` - 데이터 전송 객체
2. `dao/` - 데이터 접근 객체 (MyBatis Mapper)
3. `service/` - 비즈니스 로직
4. `controller/` - REST API 컨트롤러
5. `mapper/` - MyBatis XML 매퍼

### 2. API 응답 형식
모든 API는 `ApiResponse<T>` 형식으로 통일되어 있습니다:

```json
{
  "success": true,
  "message": "성공",
  "data": { ... }
}
```

### 3. 예외 처리
`GlobalExceptionHandler`에서 모든 예외를 처리하며, 적절한 HTTP 상태 코드와 에러 메시지를 반환합니다.

## 라이선스

이 프로젝트는 MIT 라이선스 하에 있습니다.

## 팀원

- **이우성** - 회원 관리, 인증 시스템
- **유기남** - 운동 프로그램, AI 분석
- **나미리** - 일정 관리, 캘린더 시스템
- **김동희** - 통계/분석, 관리자 기능
- **프로젝트 총괄** - 백엔드 아키텍처, 시스템 통합



## 🚀 Docker 환경 실행

### 전체 환경 시작
```bash
docker-compose up -d
```

### 백엔드만 재빌드 및 시작
```bash
docker-compose up -d --build backend
```

### 컨테이너 상태 확인
```bash
docker-compose ps
```

### 로그 확인
```bash
# 백엔드 로그
docker-compose logs backend

# MySQL 로그
docker-compose logs mysql

# 모든 서비스 로그
docker-compose logs
```

### 환경 정리
```bash
# 컨테이너 중지
docker-compose down

# 컨테이너 및 볼륨 삭제
docker-compose down -v
```


## 🌐 접속 정보

- **백엔드 API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **phpMyAdmin**: http://localhost:8081
- **MySQL**: localhost:3306