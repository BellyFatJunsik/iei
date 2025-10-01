package kr.co.iei.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Daily {
    private Long id;
    private Long therapistId;
    private Long patientId;
    private Long programId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String type; // REMOTE, VISIT
    private String status; // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    private String title;
    private String description;
    private String location; // 원격재활의 경우 null
    private String meetingLink; // 원격재활의 경우 필수
    private String notes; // 치료사 메모
    private String patientNotes; // 환자 메모
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    
    // 조인을 위한 필드
    private String therapistName;
    private String patientName;
    private String programName;
}
