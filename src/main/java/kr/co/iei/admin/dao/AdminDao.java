package kr.co.iei.admin.dao;

import kr.co.iei.admin.dto.Admin;
import kr.co.iei.admin.dto.DashboardStats;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminDao {
    
    // 기본 CRUD
    int insertAdmin(Admin admin);
    Optional<Admin> selectAdminById(Long id);
    Optional<Admin> selectAdminByUsername(String username);
    int updateAdmin(Admin admin);
    int deleteAdmin(Long id);
    
    // 인증 관련
    Optional<Admin> selectAdminByUsernameAndPassword(String username, String password);
    
    // 대시보드 통계
    DashboardStats selectDashboardStats();
    
    // 월별 통계
    List<Object> selectMonthlyUserStats();
    List<Object> selectMonthlyRevenueStats();
    List<Object> selectMonthlyProgramStats();
    
    // 최근 활동
    List<Object> selectRecentActivities();
    
    // 시스템 모니터링
    List<Object> selectSystemMetrics();
}
