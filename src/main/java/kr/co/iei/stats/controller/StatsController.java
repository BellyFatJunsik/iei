package kr.co.iei.stats.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.stats.service.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@Tag(name = "통계", description = "통계 및 분석 API")
public class StatsController {
    
    private final StatsService statsService;
    
    @GetMapping("/dashboard")
    @Operation(summary = "대시보드 통계", description = "관리자 대시보드 통계 조회")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        return ResponseEntity.ok(statsService.getDashboardStats());
    }
    
    @GetMapping("/users/monthly")
    @Operation(summary = "월별 사용자 통계", description = "월별 사용자 가입 통계 조회")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getMonthlyUserStats() {
        return ResponseEntity.ok(statsService.getMonthlyUserStats());
    }
    
    @GetMapping("/patient/{patientId}/progress")
    @Operation(summary = "환자 진행률 통계", description = "특정 환자의 진행률 통계 조회")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getPatientProgressStats(@PathVariable Long patientId) {
        return ResponseEntity.ok(statsService.getPatientProgressStats(patientId));
    }
    
    @GetMapping("/therapist/{therapistId}")
    @Operation(summary = "치료사 통계", description = "특정 치료사의 통계 조회")
    @PreAuthorize("hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTherapistStats(@PathVariable Long therapistId) {
        return ResponseEntity.ok(statsService.getTherapistStats(therapistId));
    }
}
