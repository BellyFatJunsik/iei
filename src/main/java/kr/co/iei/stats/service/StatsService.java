package kr.co.iei.stats.service;

import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.stats.dao.StatsDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsService {
    
    private final StatsDao statsDao;
    
    public ApiResponse<Map<String, Object>> getDashboardStats() {
        try {
            Map<String, Object> stats = statsDao.selectDashboardStats();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("대시보드 통계 조회 중 오류 발생", e);
            return ApiResponse.error("대시보드 통계 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Map<String, Object>>> getMonthlyUserStats() {
        try {
            List<Map<String, Object>> stats = statsDao.selectMonthlyUserStats();
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("월별 사용자 통계 조회 중 오류 발생", e);
            return ApiResponse.error("월별 사용자 통계 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Map<String, Object>>> getPatientProgressStats(Long patientId) {
        try {
            List<Map<String, Object>> stats = statsDao.selectPatientProgressStats(patientId);
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("환자 진행률 통계 조회 중 오류 발생", e);
            return ApiResponse.error("환자 진행률 통계 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Map<String, Object>>> getTherapistStats(Long therapistId) {
        try {
            List<Map<String, Object>> stats = statsDao.selectTherapistStats(therapistId);
            return ApiResponse.success(stats);
        } catch (Exception e) {
            log.error("치료사 통계 조회 중 오류 발생", e);
            return ApiResponse.error("치료사 통계 조회 중 오류가 발생했습니다.");
        }
    }
}
