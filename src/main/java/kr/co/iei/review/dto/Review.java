package kr.co.iei.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Long id;
    private Long programId;
    private Long patientId;
    private Long therapistId;
    private String content;
    private Integer rating; // 1-5
    private String status; // ACTIVE, HIDDEN, DELETED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 조인을 위한 필드
    private String patientName;
    private String therapistName;
    private String programName;
    
    // 댓글 관련
    private Long parentId; // 대댓글인 경우 부모 댓글 ID
    private List<Review> replies; // 대댓글 목록
}
