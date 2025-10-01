package kr.co.iei.calendar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.iei.calendar.dto.CalendarRequest;
import kr.co.iei.calendar.dto.Daily;
import kr.co.iei.calendar.service.CalendarService;
import kr.co.iei.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
@Tag(name = "일정 관리", description = "캘린더 및 일정 관리 API")
public class CalendarController {
    
    private final CalendarService calendarService;
    
    @PostMapping
    @Operation(summary = "일정 등록", description = "새로운 일정 등록")
    @PreAuthorize("hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Daily>> createSchedule(
            @Valid @RequestBody CalendarRequest request,
            Authentication authentication) {
        String createdBy = authentication.getName();
        return ResponseEntity.ok(calendarService.createSchedule(request, createdBy));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "일정 조회", description = "특정 일정 상세 조회")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Daily>> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getSchedule(id));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "일정 수정", description = "기존 일정 수정")
    @PreAuthorize("hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Daily>> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody CalendarRequest request,
            Authentication authentication) {
        String updatedBy = authentication.getName();
        return ResponseEntity.ok(calendarService.updateSchedule(id, request, updatedBy));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "일정 삭제", description = "일정 삭제")
    @PreAuthorize("hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.deleteSchedule(id));
    }
    
    @GetMapping("/therapist/{therapistId}")
    @Operation(summary = "치료사 일정 조회", description = "특정 치료사의 모든 일정 조회")
    @PreAuthorize("hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Daily>>> getSchedulesByTherapist(@PathVariable Long therapistId) {
        return ResponseEntity.ok(calendarService.getSchedulesByTherapist(therapistId));
    }
    
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "환자 일정 조회", description = "특정 환자의 모든 일정 조회")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Daily>>> getSchedulesByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(calendarService.getSchedulesByPatient(patientId));
    }
    
    @GetMapping("/date")
    @Operation(summary = "특정 날짜 일정 조회", description = "특정 날짜의 일정 조회")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Daily>>> getSchedulesByDate(
            @RequestParam Long therapistId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(calendarService.getSchedulesByDate(therapistId, date));
    }
    
    @GetMapping("/month")
    @Operation(summary = "월별 일정 조회", description = "특정 월의 일정 조회")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Daily>>> getSchedulesByMonth(
            @RequestParam Long therapistId,
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(calendarService.getSchedulesByMonth(therapistId, year, month));
    }
    
    @GetMapping("/range")
    @Operation(summary = "기간별 일정 조회", description = "특정 기간의 일정 조회")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Daily>>> getSchedulesByDateRange(
            @RequestParam Long therapistId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(calendarService.getSchedulesByDateRange(therapistId, startDate, endDate));
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "일정 상태 변경", description = "일정 상태 변경 (SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED)")
    @PreAuthorize("hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateScheduleStatus(
            @PathVariable Long id,
            @RequestParam String status,
            Authentication authentication) {
        String updatedBy = authentication.getName();
        return ResponseEntity.ok(calendarService.updateScheduleStatus(id, status, updatedBy));
    }
    
    @GetMapping("/available")
    @Operation(summary = "예약 가능한 시간 조회", description = "특정 날짜의 예약 가능한 시간 조회")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Daily>>> getAvailableTimeSlots(
            @RequestParam Long therapistId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(calendarService.getAvailableTimeSlots(therapistId, date));
    }
}
