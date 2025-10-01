package kr.co.iei.notice.service;

import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.notice.dao.NoticeDao;
import kr.co.iei.notice.dto.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    
    private final NoticeDao noticeDao;
    
    public ApiResponse<Notice> createNotice(Notice notice) {
        try {
            int result = noticeDao.insertNotice(notice);
            if (result > 0) {
                return ApiResponse.success("공지사항이 등록되었습니다.", notice);
            } else {
                return ApiResponse.<Notice>error("공지사항 등록에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("공지사항 등록 중 오류 발생", e);
            return ApiResponse.<Notice>error("공지사항 등록 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<Notice> getNotice(Long id) {
        try {
            Notice notice = noticeDao.selectNoticeById(id);
            if (notice != null) {
                noticeDao.incrementViewCount(id);
                return ApiResponse.success(notice);
            } else {
                return ApiResponse.<Notice>error("공지사항을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            log.error("공지사항 조회 중 오류 발생", e);
            return ApiResponse.<Notice>error("공지사항 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Notice>> getAllNotices() {
        try {
            List<Notice> notices = noticeDao.selectAllNotices();
            return ApiResponse.success(notices);
        } catch (Exception e) {
            log.error("공지사항 목록 조회 중 오류 발생", e);
            return ApiResponse.<List<Notice>>error("공지사항 목록 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<Notice> updateNotice(Long id, Notice notice) {
        try {
            notice.setId(id);
            int result = noticeDao.updateNotice(notice);
            if (result > 0) {
                return ApiResponse.success("공지사항이 수정되었습니다.", notice);
            } else {
                return ApiResponse.<Notice>error("공지사항 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("공지사항 수정 중 오류 발생", e);
            return ApiResponse.<Notice>error("공지사항 수정 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<Void> deleteNotice(Long id) {
        try {
            int result = noticeDao.deleteNotice(id);
            if (result > 0) {
                return ApiResponse.<Void>success("공지사항이 삭제되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("공지사항 삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("공지사항 삭제 중 오류 발생", e);
            return ApiResponse.<Void>error("공지사항 삭제 중 오류가 발생했습니다.");
        }
    }
}
