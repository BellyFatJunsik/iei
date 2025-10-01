package kr.co.iei.admin.service;

import kr.co.iei.admin.dao.AdminDao;
import kr.co.iei.admin.dto.Admin;
import kr.co.iei.admin.dto.DashboardStats;
import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final AdminDao adminDao;
    private final MemberService memberService;
    
    public ApiResponse<DashboardStats> getDashboardStats() {
        try {
            DashboardStats stats = adminDao.selectDashboardStats();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("대시보드 통계 조회 중 오류 발생", e);
            return ApiResponse.error("대시보드 통계 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Object>> getMonthlyUserStats() {
        try {
            List<Object> stats = adminDao.selectMonthlyUserStats();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("월별 사용자 통계 조회 중 오류 발생", e);
            return ApiResponse.error("월별 사용자 통계 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Object>> getMonthlyRevenueStats() {
        try {
            List<Object> stats = adminDao.selectMonthlyRevenueStats();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("월별 수익 통계 조회 중 오류 발생", e);
            return ApiResponse.error("월별 수익 통계 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Object>> getMonthlyProgramStats() {
        try {
            List<Object> stats = adminDao.selectMonthlyProgramStats();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("월별 프로그램 통계 조회 중 오류 발생", e);
            return ApiResponse.error("월별 프로그램 통계 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Object>> getRecentActivities() {
        try {
            List<Object> activities = adminDao.selectRecentActivities();
            return ApiResponse.success(activities);
        } catch (Exception e) {
            log.error("최근 활동 조회 중 오류 발생", e);
            return ApiResponse.error("최근 활동 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Object>> getSystemMetrics() {
        try {
            List<Object> metrics = adminDao.selectSystemMetrics();
            return ApiResponse.success(metrics);
        } catch (Exception e) {
            log.error("시스템 메트릭 조회 중 오류 발생", e);
            return ApiResponse.error("시스템 메트릭 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<Admin> getAdminProfile(String username) {
        try {
            Optional<Admin> adminOpt = adminDao.selectAdminByUsername(username);
            
            if (adminOpt.isEmpty()) {
                return ApiResponse.error("관리자 정보를 찾을 수 없습니다.");
            }
            
            return ApiResponse.success(adminOpt.get());
        } catch (Exception e) {
            log.error("관리자 프로필 조회 중 오류 발생", e);
            return ApiResponse.error("관리자 프로필 조회 중 오류가 발생했습니다.");
        }
    }
}
