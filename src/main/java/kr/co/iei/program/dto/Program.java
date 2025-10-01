package kr.co.iei.program.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Program {
    private Long id;
    private String name;
    private String description;
    private String category; // REHABILITATION, STRENGTH, FLEXIBILITY, BALANCE, etc.
    private String difficulty; // BEGINNER, INTERMEDIATE, ADVANCED
    private Integer duration; // 분 단위
    private String targetMuscles; // 대상 근육군 (JSON 형태)
    private String equipment; // 필요 장비 (JSON 형태)
    private String instructions; // 운동 방법 (JSON 형태)
    private String videoUrl; // 운동 영상 URL
    private String thumbnailUrl; // 썸네일 이미지 URL
    private String status; // ACTIVE, INACTIVE, DRAFT
    private Long createdBy; // 생성자 ID
    private String createdByName; // 생성자 이름
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 통계 필드
    private Integer viewCount;
    private Integer likeCount;
    private Integer completedCount;
    private Double averageRating;
    
    // AI 분석 결과
    private String aiAnalysis; // AI 분석 결과 (JSON 형태)
    private String recommendedFor; // 추천 대상 (JSON 형태)
    private String contraindications; // 금기사항 (JSON 형태)
    
    // 운동 단계별 정보
    private List<ExerciseStep> exerciseSteps;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ExerciseStep {
    private Integer stepNumber;
    private String stepName;
    private String description;
    private String imageUrl;
    private String videoUrl;
    private Integer duration; // 초 단위
    private Integer repetitions;
    private String notes;
}
