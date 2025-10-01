package kr.co.iei.program.service;

import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.common.util.PageInfoUtil;
import kr.co.iei.common.dto.PageInfo;
import kr.co.iei.program.dao.ProgramDao;
import kr.co.iei.program.dto.Program;
import kr.co.iei.program.dto.ProgramCompletion;
import kr.co.iei.program.dto.ProgramRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramService {
    
    private final ProgramDao programDao;
    
    @Transactional
    public ApiResponse<Program> createProgram(ProgramRequest request, Long createdBy) {
        try {
            Program program = Program.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .category(request.getCategory())
                    .difficulty(request.getDifficulty())
                    .duration(request.getDuration())
                    .targetMuscles(request.getTargetMuscles())
                    .equipment(request.getEquipment())
                    .instructions(request.getInstructions())
                    .videoUrl(request.getVideoUrl())
                    .thumbnailUrl(request.getThumbnailUrl())
                    .status("ACTIVE")
                    .createdBy(createdBy)
                    .aiAnalysis(request.getAiAnalysis())
                    .recommendedFor(request.getRecommendedFor())
                    .contraindications(request.getContraindications())
                    .viewCount(0)
                    .likeCount(0)
                    .completedCount(0)
                    .averageRating(0.0)
                    .build();
            
            int result = programDao.insertProgram(program);
            
            if (result > 0) {
                return ApiResponse.success("프로그램이 등록되었습니다.", program);
            } else {
                return ApiResponse.<Program>error("프로그램 등록에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("프로그램 등록 중 오류 발생", e);
            return ApiResponse.<Program>error("프로그램 등록 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<Program> getProgram(Long id) {
        try {
            Optional<Program> programOpt = programDao.selectProgramById(id);
            
            if (programOpt.isEmpty()) {
                return ApiResponse.<Program>error("프로그램을 찾을 수 없습니다.");
            }
            
            // 조회수 증가
            programDao.incrementViewCount(id);
            
            return ApiResponse.success(programOpt.get());
            
        } catch (Exception e) {
            log.error("프로그램 조회 중 오류 발생", e);
            return ApiResponse.<Program>error("프로그램 조회 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Program> updateProgram(Long id, ProgramRequest request) {
        try {
            Optional<Program> existingProgramOpt = programDao.selectProgramById(id);
            
            if (existingProgramOpt.isEmpty()) {
                return ApiResponse.<Program>error("프로그램을 찾을 수 없습니다.");
            }
            
            Program program = existingProgramOpt.get();
            program.setName(request.getName());
            program.setDescription(request.getDescription());
            program.setCategory(request.getCategory());
            program.setDifficulty(request.getDifficulty());
            program.setDuration(request.getDuration());
            program.setTargetMuscles(request.getTargetMuscles());
            program.setEquipment(request.getEquipment());
            program.setInstructions(request.getInstructions());
            program.setVideoUrl(request.getVideoUrl());
            program.setThumbnailUrl(request.getThumbnailUrl());
            program.setAiAnalysis(request.getAiAnalysis());
            program.setRecommendedFor(request.getRecommendedFor());
            program.setContraindications(request.getContraindications());
            
            int result = programDao.updateProgram(program);
            
            if (result > 0) {
                return ApiResponse.success("프로그램이 수정되었습니다.", program);
            } else {
                return ApiResponse.<Program>error("프로그램 수정에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("프로그램 수정 중 오류 발생", e);
            return ApiResponse.<Program>error("프로그램 수정 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Void> deleteProgram(Long id) {
        try {
            int result = programDao.deleteProgram(id);
            
            if (result > 0) {
                return ApiResponse.<Void>success("프로그램이 삭제되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("프로그램 삭제에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("프로그램 삭제 중 오류 발생", e);
            return ApiResponse.<Void>error("프로그램 삭제 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Program>> getAllPrograms() {
        try {
            List<Program> programs = programDao.selectAllPrograms();
            return ApiResponse.success(programs);
        } catch (Exception e) {
            log.error("프로그램 목록 조회 중 오류 발생", e);
            return ApiResponse.<List<Program>>error("프로그램 목록 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Program>> getProgramsByCategory(String category) {
        try {
            List<Program> programs = programDao.selectProgramsByCategory(category);
            return ApiResponse.success(programs);
        } catch (Exception e) {
            log.error("카테고리별 프로그램 조회 중 오류 발생", e);
            return ApiResponse.<List<Program>>error("카테고리별 프로그램 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Program>> getProgramsByDifficulty(String difficulty) {
        try {
            List<Program> programs = programDao.selectProgramsByDifficulty(difficulty);
            return ApiResponse.success(programs);
        } catch (Exception e) {
            log.error("난이도별 프로그램 조회 중 오류 발생", e);
            return ApiResponse.<List<Program>>error("난이도별 프로그램 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Program>> searchPrograms(String keyword, String category, String difficulty, int page, int size) {
        try {
            int offset = PageInfoUtil.getOffset(page, size);
            List<Program> programs = programDao.searchPrograms(keyword, category, difficulty, offset, size);
            return ApiResponse.success(programs);
        } catch (Exception e) {
            log.error("프로그램 검색 중 오류 발생", e);
            return ApiResponse.<List<Program>>error("프로그램 검색 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Program>> getRecommendedPrograms(Long patientId) {
        try {
            List<Program> programs = programDao.selectRecommendedPrograms(patientId, 10);
            return ApiResponse.success(programs);
        } catch (Exception e) {
            log.error("추천 프로그램 조회 중 오류 발생", e);
            return ApiResponse.<List<Program>>error("추천 프로그램 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Program>> getPopularPrograms() {
        try {
            List<Program> programs = programDao.selectPopularPrograms(10);
            return ApiResponse.success(programs);
        } catch (Exception e) {
            log.error("인기 프로그램 조회 중 오류 발생", e);
            return ApiResponse.<List<Program>>error("인기 프로그램 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Program>> getRecentPrograms() {
        try {
            List<Program> programs = programDao.selectRecentPrograms(10);
            return ApiResponse.success(programs);
        } catch (Exception e) {
            log.error("최신 프로그램 조회 중 오류 발생", e);
            return ApiResponse.<List<Program>>error("최신 프로그램 조회 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<ProgramCompletion> completeProgram(ProgramCompletion completion) {
        try {
            int result = programDao.insertProgramCompletion(completion);
            
            if (result > 0) {
                // 프로그램 완료 수 증가
                programDao.incrementCompletedCount(completion.getProgramId());
                
                // 평점 업데이트 (필요시)
                if (completion.getSatisfactionRating() != null) {
                    updateProgramRating(completion.getProgramId());
                }
                
                return ApiResponse.success("프로그램 완료가 기록되었습니다.", completion);
            } else {
                return ApiResponse.<ProgramCompletion>error("프로그램 완료 기록에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("프로그램 완료 기록 중 오류 발생", e);
            return ApiResponse.<ProgramCompletion>error("프로그램 완료 기록 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<ProgramCompletion>> getCompletionsByPatient(Long patientId) {
        try {
            List<ProgramCompletion> completions = programDao.selectCompletionsByPatient(patientId);
            return ApiResponse.success(completions);
        } catch (Exception e) {
            log.error("환자 완료 기록 조회 중 오류 발생", e);
            return ApiResponse.<List<ProgramCompletion>>error("환자 완료 기록 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<ProgramCompletion>> getCompletionsByProgram(Long programId) {
        try {
            List<ProgramCompletion> completions = programDao.selectCompletionsByProgram(programId);
            return ApiResponse.success(completions);
        } catch (Exception e) {
            log.error("프로그램 완료 기록 조회 중 오류 발생", e);
            return ApiResponse.<List<ProgramCompletion>>error("프로그램 완료 기록 조회 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Void> likeProgram(Long id) {
        try {
            int result = programDao.incrementLikeCount(id);
            
            if (result > 0) {
                return ApiResponse.<Void>success("좋아요가 추가되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("좋아요 추가에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("좋아요 추가 중 오류 발생", e);
            return ApiResponse.<Void>error("좋아요 추가 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Void> unlikeProgram(Long id) {
        try {
            int result = programDao.decrementLikeCount(id);
            
            if (result > 0) {
                return ApiResponse.<Void>success("좋아요가 취소되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("좋아요 취소에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("좋아요 취소 중 오류 발생", e);
            return ApiResponse.<Void>error("좋아요 취소 중 오류가 발생했습니다.");
        }
    }
    
    private void updateProgramRating(Long programId) {
        try {
            // 프로그램의 평균 평점을 계산하여 업데이트
            // 실제 구현에서는 완료 기록들의 평점을 평균내어 계산
            // 여기서는 간단히 예시로 처리
        } catch (Exception e) {
            log.error("프로그램 평점 업데이트 중 오류 발생", e);
        }
    }
}
