package kr.co.iei.stats.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatsDao {
    // 대시보드 통계
    Map<String, Object> selectDashboardStats();
    
    // 월별 통계
    List<Map<String, Object>> selectMonthlyUserStats();
    List<Map<String, Object>> selectMonthlyRevenueStats();
    List<Map<String, Object>> selectMonthlyProgramStats();
    
    // 환자별 통계
    List<Map<String, Object>> selectPatientProgressStats(@Param("patientId") Long patientId);
    List<Map<String, Object>> selectPatientCompletionStats(@Param("patientId") Long patientId);
    
    // 치료사별 통계
    List<Map<String, Object>> selectTherapistStats(@Param("therapistId") Long therapistId);
    
    // 프로그램별 통계
    List<Map<String, Object>> selectProgramStats(@Param("programId") Long programId);
    
    // 시스템 모니터링
    List<Map<String, Object>> selectSystemMetrics();
}
