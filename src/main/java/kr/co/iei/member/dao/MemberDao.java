package kr.co.iei.member.dao;

import kr.co.iei.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberDao {
    
    // 기본 CRUD
    int insertMember(Member member);
    Optional<Member> selectMemberById(Long id);
    Optional<Member> selectMemberByUsername(String username);
    int updateMember(Member member);
    int deleteMember(Long id);
    
    // 인증 관련
    Optional<Member> selectMemberByUsernameAndPassword(@Param("username") String username, 
                                                      @Param("password") String password);
    
    // 이메일 중복 확인
    boolean existsByEmail(String email);
    
    // 아이디 중복 확인
    boolean existsByUsername(String username);
    
    // 역할별 조회
    List<Member> selectMembersByRole(String role);
    List<Member> selectMembersByRoleWithPaging(@Param("role") String role, 
                                             @Param("offset") int offset, 
                                             @Param("limit") int limit);
    
    // 물리치료사 승인 대기 목록
    List<Member> selectPendingTherapists();
    
    // 물리치료사 승인
    int approveTherapist(Long id);
    
    // 물리치료사 거부
    int rejectTherapist(Long id);
    
    // 상태별 조회
    List<Member> selectMembersByStatus(String status);
    
    // 검색
    List<Member> searchMembers(@Param("keyword") String keyword, 
                              @Param("role") String role,
                              @Param("offset") int offset, 
                              @Param("limit") int limit);
    
    // 총 개수 조회
    int countMembersByRole(String role);
    int countMembersByStatus(String status);
    int countSearchResults(@Param("keyword") String keyword, @Param("role") String role);
}
