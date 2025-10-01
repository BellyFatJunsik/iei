package kr.co.iei.calendar.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarRequest {
    @NotNull(message = "치료사 ID는 필수입니다")
    private Long therapistId;
    
    private Long patientId;
    private Long programId;
    
    @NotNull(message = "날짜는 필수입니다")
    private LocalDate date;
    
    @NotNull(message = "시작 시간은 필수입니다")
    private LocalTime startTime;
    
    @NotNull(message = "종료 시간은 필수입니다")
    private LocalTime endTime;
    
    @NotNull(message = "일정 유형은 필수입니다")
    private String type; // REMOTE, VISIT
    
    private String title;
    private String description;
    private String location;
    private String meetingLink;
    private String notes;
}
