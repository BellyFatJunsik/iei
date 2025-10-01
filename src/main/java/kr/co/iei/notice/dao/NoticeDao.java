package kr.co.iei.notice.dao;

import kr.co.iei.notice.dto.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeDao {
    int insertNotice(Notice notice);
    Notice selectNoticeById(Long id);
    List<Notice> selectAllNotices();
    List<Notice> selectNoticesByCategory(String category);
    List<Notice> selectNoticesByStatus(String status);
    int updateNotice(Notice notice);
    int deleteNotice(Long id);
    int incrementViewCount(Long id);
    int countNoticesByCategory(String category);
}
