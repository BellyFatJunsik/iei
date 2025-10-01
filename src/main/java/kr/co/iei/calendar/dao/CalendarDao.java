package kr.co.iei.calendar.dao;

import kr.co.iei.calendar.dto.Daily;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface CalendarDao {
    
    // 기본 CRUD
    int insertSchedule(Daily daily);
    Optional<Daily> selectScheduleById(Long id);
    int updateSchedule(Daily daily);
    int deleteSchedule(Long id);
    
    // 일정 조회
    List<Daily> selectSchedulesByTherapistId(Long therapistId);
    List<Daily> selectSchedulesByPatientId(Long patientId);
    List<Daily> selectSchedulesByDateRange(@Param("therapistId") Long therapistId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
    
    // 특정 날짜 일정 조회
    List<Daily> selectSchedulesByDate(@Param("therapistId") Long therapistId,
                                     @Param("date") LocalDate date);
    
    // 월별 일정 조회
    List<Daily> selectSchedulesByMonth(@Param("therapistId") Long therapistId,
                                      @Param("year") int year,
                                      @Param("month") int month);
    
    // 시간 충돌 확인
    boolean hasTimeConflict(@Param("therapistId") Long therapistId,
                           @Param("date") LocalDate date,
                           @Param("startTime") String startTime,
                           @Param("endTime") String endTime,
                           @Param("excludeId") Long excludeId);
    
    // 상태별 조회
    List<Daily> selectSchedulesByStatus(String status);
    List<Daily> selectSchedulesByTherapistAndStatus(@Param("therapistId") Long therapistId,
                                                   @Param("status") String status);
    
    // 예약 가능한 시간 조회
    List<Daily> selectAvailableTimeSlots(@Param("therapistId") Long therapistId,
                                        @Param("date") LocalDate date);
    
    // 완료된 일정 조회
    List<Daily> selectCompletedSchedules(@Param("therapistId") Long therapistId,
                                        @Param("patientId") Long patientId);
    
    // 통계
    int countSchedulesByTherapist(Long therapistId);
    int countSchedulesByStatus(String status);
    int countSchedulesByType(String type);
    
    // 최근 일정
    List<Daily> selectRecentSchedules(@Param("therapistId") Long therapistId,
                                     @Param("limit") int limit);
}
