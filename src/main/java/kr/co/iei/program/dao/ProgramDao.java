package kr.co.iei.program.dao;

import kr.co.iei.program.dto.Program;
import kr.co.iei.program.dto.ProgramCompletion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProgramDao {
    
    // 기본 CRUD
    int insertProgram(Program program);
    Optional<Program> selectProgramById(Long id);
    int updateProgram(Program program);
    int deleteProgram(Long id);
    
    // 프로그램 목록 조회
    List<Program> selectAllPrograms();
    List<Program> selectProgramsByCategory(String category);
    List<Program> selectProgramsByDifficulty(String difficulty);
    List<Program> selectProgramsByStatus(String status);
    List<Program> selectProgramsByCreator(Long createdBy);
    
    // 검색
    List<Program> searchPrograms(@Param("keyword") String keyword,
                                @Param("category") String category,
                                @Param("difficulty") String difficulty,
                                @Param("offset") int offset,
                                @Param("limit") int limit);
    
    // 추천 프로그램
    List<Program> selectRecommendedPrograms(@Param("patientId") Long patientId,
                                           @Param("limit") int limit);
    
    // 인기 프로그램
    List<Program> selectPopularPrograms(@Param("limit") int limit);
    
    // 최신 프로그램
    List<Program> selectRecentPrograms(@Param("limit") int limit);
    
    // 프로그램 완료 관련
    int insertProgramCompletion(ProgramCompletion completion);
    List<ProgramCompletion> selectCompletionsByPatient(Long patientId);
    List<ProgramCompletion> selectCompletionsByProgram(Long programId);
    List<ProgramCompletion> selectCompletionsByTherapist(Long therapistId);
    
    // 통계
    int countProgramsByCategory(String category);
    int countProgramsByDifficulty(String difficulty);
    int countProgramsByStatus(String status);
    int countCompletionsByProgram(Long programId);
    int countCompletionsByPatient(Long patientId);
    
    // 조회수 증가
    int incrementViewCount(Long id);
    
    // 좋아요 수 증가/감소
    int incrementLikeCount(Long id);
    int decrementLikeCount(Long id);
    
    // 평점 업데이트
    int updateAverageRating(@Param("id") Long id, @Param("rating") Double rating);
    
    // AI 분석 결과 업데이트
    int updateAiAnalysis(@Param("id") Long id, @Param("analysis") String analysis);
    
    // 프로그램 완료 횟수 증가
    int incrementCompletedCount(Long programId);
}
