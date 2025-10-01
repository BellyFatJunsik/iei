package kr.co.iei.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private Long id;
    private String title;
    private String content;
    private String category; // GENERAL, SYSTEM, MAINTENANCE, etc.
    private String status; // ACTIVE, INACTIVE, DRAFT
    private Long createdBy;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer viewCount;
    private String priority; // HIGH, MEDIUM, LOW
    private LocalDateTime publishedAt;
}
