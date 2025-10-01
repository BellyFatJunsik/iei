package kr.co.iei.program.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramCompletion {
    private Long id;
    private Long programId;
    private Long patientId;
    private Long therapistId;
    private LocalDateTime completedAt;
    private Integer actualDuration; // 실제 소요 시간 (분)
    private String completionNotes; // 완료 메모
    private String patientFeedback; // 환자 피드백
    private Integer difficultyRating; // 1-5 난이도 평가
    private Integer satisfactionRating; // 1-5 만족도 평가
    private String status; // COMPLETED, PARTIAL, SKIPPED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 조인을 위한 필드
    private String programName;
    private String patientName;
    private String therapistName;
}
