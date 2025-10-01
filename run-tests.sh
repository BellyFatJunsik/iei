#!/bin/bash

# PT Project Backend Test Runner
echo "🧪 PT Project Backend 테스트 실행 중..."

# 테스트 실행
echo "📋 단위 테스트 실행..."
mvn test

# 테스트 결과 확인
if [ $? -eq 0 ]; then
    echo "✅ 모든 테스트가 성공적으로 완료되었습니다!"
else
    echo "❌ 일부 테스트가 실패했습니다."
    exit 1
fi

echo "🎉 테스트 실행 완료!"
