package kr.co.iei.calendar.service;

import kr.co.iei.calendar.dao.CalendarDao;
import kr.co.iei.calendar.dto.CalendarRequest;
import kr.co.iei.calendar.dto.Daily;
import kr.co.iei.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarService {
    
    private final CalendarDao calendarDao;
    
    @Transactional
    public ApiResponse<Daily> createSchedule(CalendarRequest request, String createdBy) {
        try {
            // 시간 충돌 확인
            boolean hasConflict = calendarDao.hasTimeConflict(
                    request.getTherapistId(),
                    request.getDate(),
                    request.getStartTime().toString(),
                    request.getEndTime().toString(),
                    null
            );
            
            if (hasConflict) {
                return ApiResponse.<Daily>error("해당 시간에 이미 일정이 있습니다.");
            }
            
            Daily daily = Daily.builder()
                    .therapistId(request.getTherapistId())
                    .patientId(request.getPatientId())
                    .programId(request.getProgramId())
                    .date(request.getDate())
                    .startTime(request.getStartTime())
                    .endTime(request.getEndTime())
                    .type(request.getType())
                    .status("SCHEDULED")
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .location(request.getLocation())
                    .meetingLink(request.getMeetingLink())
                    .notes(request.getNotes())
                    .createdBy(createdBy)
                    .build();
            
            int result = calendarDao.insertSchedule(daily);
            
            if (result > 0) {
                return ApiResponse.success("일정이 등록되었습니다.", daily);
            } else {
                return ApiResponse.<Daily>error("일정 등록에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("일정 등록 중 오류 발생", e);
            return ApiResponse.<Daily>error("일정 등록 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<Daily> getSchedule(Long id) {
        try {
            Optional<Daily> scheduleOpt = calendarDao.selectScheduleById(id);
            
            if (scheduleOpt.isEmpty()) {
                return ApiResponse.<Daily>error("일정을 찾을 수 없습니다.");
            }
            
            return ApiResponse.success(scheduleOpt.get());
            
        } catch (Exception e) {
            log.error("일정 조회 중 오류 발생", e);
            return ApiResponse.<Daily>error("일정 조회 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Daily> updateSchedule(Long id, CalendarRequest request, String updatedBy) {
        try {
            Optional<Daily> existingScheduleOpt = calendarDao.selectScheduleById(id);
            
            if (existingScheduleOpt.isEmpty()) {
                return ApiResponse.<Daily>error("일정을 찾을 수 없습니다.");
            }
            
            // 시간 충돌 확인 (자기 자신 제외)
            boolean hasConflict = calendarDao.hasTimeConflict(
                    request.getTherapistId(),
                    request.getDate(),
                    request.getStartTime().toString(),
                    request.getEndTime().toString(),
                    id
            );
            
            if (hasConflict) {
                return ApiResponse.<Daily>error("해당 시간에 이미 일정이 있습니다.");
            }
            
            Daily daily = existingScheduleOpt.get();
            daily.setTherapistId(request.getTherapistId());
            daily.setPatientId(request.getPatientId());
            daily.setProgramId(request.getProgramId());
            daily.setDate(request.getDate());
            daily.setStartTime(request.getStartTime());
            daily.setEndTime(request.getEndTime());
            daily.setType(request.getType());
            daily.setTitle(request.getTitle());
            daily.setDescription(request.getDescription());
            daily.setLocation(request.getLocation());
            daily.setMeetingLink(request.getMeetingLink());
            daily.setNotes(request.getNotes());
            daily.setUpdatedBy(updatedBy);
            
            int result = calendarDao.updateSchedule(daily);
            
            if (result > 0) {
                return ApiResponse.success("일정이 수정되었습니다.", daily);
            } else {
                return ApiResponse.<Daily>error("일정 수정에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("일정 수정 중 오류 발생", e);
            return ApiResponse.<Daily>error("일정 수정 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Void> deleteSchedule(Long id) {
        try {
            int result = calendarDao.deleteSchedule(id);
            
            if (result > 0) {
                return ApiResponse.<Void>success("일정이 삭제되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("일정 삭제에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("일정 삭제 중 오류 발생", e);
            return ApiResponse.<Void>error("일정 삭제 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Daily>> getSchedulesByTherapist(Long therapistId) {
        try {
            List<Daily> schedules = calendarDao.selectSchedulesByTherapistId(therapistId);
            return ApiResponse.success(schedules);
        } catch (Exception e) {
            log.error("치료사 일정 조회 중 오류 발생", e);
            return ApiResponse.<List<Daily>>error("치료사 일정 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Daily>> getSchedulesByPatient(Long patientId) {
        try {
            List<Daily> schedules = calendarDao.selectSchedulesByPatientId(patientId);
            return ApiResponse.success(schedules);
        } catch (Exception e) {
            log.error("환자 일정 조회 중 오류 발생", e);
            return ApiResponse.<List<Daily>>error("환자 일정 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Daily>> getSchedulesByDate(Long therapistId, LocalDate date) {
        try {
            List<Daily> schedules = calendarDao.selectSchedulesByDate(therapistId, date);
            return ApiResponse.success(schedules);
        } catch (Exception e) {
            log.error("특정 날짜 일정 조회 중 오류 발생", e);
            return ApiResponse.<List<Daily>>error("특정 날짜 일정 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Daily>> getSchedulesByMonth(Long therapistId, int year, int month) {
        try {
            List<Daily> schedules = calendarDao.selectSchedulesByMonth(therapistId, year, month);
            return ApiResponse.success(schedules);
        } catch (Exception e) {
            log.error("월별 일정 조회 중 오류 발생", e);
            return ApiResponse.<List<Daily>>error("월별 일정 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Daily>> getSchedulesByDateRange(Long therapistId, LocalDate startDate, LocalDate endDate) {
        try {
            List<Daily> schedules = calendarDao.selectSchedulesByDateRange(therapistId, startDate, endDate);
            return ApiResponse.success(schedules);
        } catch (Exception e) {
            log.error("기간별 일정 조회 중 오류 발생", e);
            return ApiResponse.<List<Daily>>error("기간별 일정 조회 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Void> updateScheduleStatus(Long id, String status, String updatedBy) {
        try {
            Optional<Daily> scheduleOpt = calendarDao.selectScheduleById(id);
            
            if (scheduleOpt.isEmpty()) {
                return ApiResponse.<Void>error("일정을 찾을 수 없습니다.");
            }
            
            Daily daily = scheduleOpt.get();
            daily.setStatus(status);
            daily.setUpdatedBy(updatedBy);
            
            int result = calendarDao.updateSchedule(daily);
            
            if (result > 0) {
                return ApiResponse.<Void>success("일정 상태가 변경되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("일정 상태 변경에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("일정 상태 변경 중 오류 발생", e);
            return ApiResponse.<Void>error("일정 상태 변경 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Daily>> getAvailableTimeSlots(Long therapistId, LocalDate date) {
        try {
            List<Daily> timeSlots = calendarDao.selectAvailableTimeSlots(therapistId, date);
            return ApiResponse.success(timeSlots);
        } catch (Exception e) {
            log.error("예약 가능한 시간 조회 중 오류 발생", e);
            return ApiResponse.<List<Daily>>error("예약 가능한 시간 조회 중 오류가 발생했습니다.");
        }
    }
}
