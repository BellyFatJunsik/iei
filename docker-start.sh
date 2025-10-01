#!/bin/bash

echo "🚀 PT Project Backend Docker 환경 시작하기"
echo "=========================================="

# Docker Compose로 서비스 시작
echo "📦 Docker Compose로 서비스들을 시작합니다..."
docker-compose up -d

echo ""
echo "⏳ 서비스들이 시작되는 동안 잠시 기다려주세요..."
sleep 10

# 서비스 상태 확인
echo ""
echo "📊 서비스 상태 확인:"
docker-compose ps

echo ""
echo "🌐 접속 정보:"
echo "  - 백엔드 API: http://localhost:8080"
echo "  - Swagger UI: http://localhost:8080/swagger-ui.html"
echo "  - phpMyAdmin: http://localhost:8081"
echo "    - 사용자명: ptuser"
echo "    - 비밀번호: ptpass123"

echo ""
echo "📝 로그 확인 명령어:"
echo "  - 전체 로그: docker-compose logs -f"
echo "  - 백엔드 로그: docker-compose logs -f backend"
echo "  - MySQL 로그: docker-compose logs -f mysql"

echo ""
echo "🛑 서비스 중지 명령어:"
echo "  - docker-compose down"

echo ""
echo "✅ 설정 완료! 이제 백엔드 API를 사용할 수 있습니다."
