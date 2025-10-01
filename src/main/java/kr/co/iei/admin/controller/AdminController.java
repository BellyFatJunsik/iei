package kr.co.iei.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.iei.admin.dto.Admin;
import kr.co.iei.admin.dto.DashboardStats;
import kr.co.iei.admin.service.AdminService;
import kr.co.iei.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "관리자", description = "관리자 기능 API")
public class AdminController {
    
    private final AdminService adminService;
    
    @GetMapping("/dashboard")
    @Operation(summary = "대시보드 통계", description = "관리자 대시보드 통계 정보 조회")
    public ResponseEntity<ApiResponse<DashboardStats>> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }
    
    @GetMapping("/stats/users/monthly")
    @Operation(summary = "월별 사용자 통계", description = "월별 사용자 가입 통계 조회")
    public ResponseEntity<ApiResponse<List<Object>>> getMonthlyUserStats() {
        return ResponseEntity.ok(adminService.getMonthlyUserStats());
    }
    
    @GetMapping("/stats/revenue/monthly")
    @Operation(summary = "월별 수익 통계", description = "월별 수익 통계 조회")
    public ResponseEntity<ApiResponse<List<Object>>> getMonthlyRevenueStats() {
        return ResponseEntity.ok(adminService.getMonthlyRevenueStats());
    }
    
    @GetMapping("/stats/programs/monthly")
    @Operation(summary = "월별 프로그램 통계", description = "월별 프로그램 통계 조회")
    public ResponseEntity<ApiResponse<List<Object>>> getMonthlyProgramStats() {
        return ResponseEntity.ok(adminService.getMonthlyProgramStats());
    }
    
    @GetMapping("/activities/recent")
    @Operation(summary = "최근 활동", description = "최근 시스템 활동 내역 조회")
    public ResponseEntity<ApiResponse<List<Object>>> getRecentActivities() {
        return ResponseEntity.ok(adminService.getRecentActivities());
    }
    
    @GetMapping("/system/metrics")
    @Operation(summary = "시스템 메트릭", description = "시스템 성능 메트릭 조회")
    public ResponseEntity<ApiResponse<List<Object>>> getSystemMetrics() {
        return ResponseEntity.ok(adminService.getSystemMetrics());
    }
    
    @GetMapping("/profile")
    @Operation(summary = "관리자 프로필", description = "현재 관리자 프로필 정보 조회")
    public ResponseEntity<ApiResponse<Admin>> getProfile(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(adminService.getAdminProfile(username));
    }
}
