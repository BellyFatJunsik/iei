package kr.co.iei.program.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.program.dto.Program;
import kr.co.iei.program.dto.ProgramCompletion;
import kr.co.iei.program.dto.ProgramRequest;
import kr.co.iei.program.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/program")
@RequiredArgsConstructor
@Tag(name = "운동 프로그램", description = "운동 프로그램 관리 API")
public class ProgramController {
    
    private final ProgramService programService;
    
    @PostMapping
    @Operation(summary = "프로그램 등록", description = "새로운 운동 프로그램 등록")
    @PreAuthorize("hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Program>> createProgram(
            @Valid @RequestBody ProgramRequest request,
            Authentication authentication) {
        // 실제로는 authentication에서 사용자 ID를 가져와야 함
        Long createdBy = 1L; // 임시값
        return ResponseEntity.ok(programService.createProgram(request, createdBy));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "프로그램 조회", description = "특정 프로그램 상세 조회")
    public ResponseEntity<ApiResponse<Program>> getProgram(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgram(id));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "프로그램 수정", description = "기존 프로그램 수정")
    @PreAuthorize("hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Program>> updateProgram(
            @PathVariable Long id,
            @Valid @RequestBody ProgramRequest request) {
        return ResponseEntity.ok(programService.updateProgram(id, request));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "프로그램 삭제", description = "프로그램 삭제")
    @PreAuthorize("hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProgram(@PathVariable Long id) {
        return ResponseEntity.ok(programService.deleteProgram(id));
    }
    
    @GetMapping
    @Operation(summary = "프로그램 목록", description = "전체 프로그램 목록 조회")
    public ResponseEntity<ApiResponse<List<Program>>> getAllPrograms() {
        return ResponseEntity.ok(programService.getAllPrograms());
    }
    
    @GetMapping("/category/{category}")
    @Operation(summary = "카테고리별 프로그램", description = "특정 카테고리의 프로그램 조회")
    public ResponseEntity<ApiResponse<List<Program>>> getProgramsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(programService.getProgramsByCategory(category));
    }
    
    @GetMapping("/difficulty/{difficulty}")
    @Operation(summary = "난이도별 프로그램", description = "특정 난이도의 프로그램 조회")
    public ResponseEntity<ApiResponse<List<Program>>> getProgramsByDifficulty(@PathVariable String difficulty) {
        return ResponseEntity.ok(programService.getProgramsByDifficulty(difficulty));
    }
    
    @GetMapping("/search")
    @Operation(summary = "프로그램 검색", description = "프로그램 검색")
    public ResponseEntity<ApiResponse<List<Program>>> searchPrograms(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(programService.searchPrograms(keyword, category, difficulty, page, size));
    }
    
    @GetMapping("/recommended")
    @Operation(summary = "추천 프로그램", description = "환자별 추천 프로그램 조회")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Program>>> getRecommendedPrograms(
            @RequestParam Long patientId) {
        return ResponseEntity.ok(programService.getRecommendedPrograms(patientId));
    }
    
    @GetMapping("/popular")
    @Operation(summary = "인기 프로그램", description = "인기 프로그램 조회")
    public ResponseEntity<ApiResponse<List<Program>>> getPopularPrograms() {
        return ResponseEntity.ok(programService.getPopularPrograms());
    }
    
    @GetMapping("/recent")
    @Operation(summary = "최신 프로그램", description = "최신 프로그램 조회")
    public ResponseEntity<ApiResponse<List<Program>>> getRecentPrograms() {
        return ResponseEntity.ok(programService.getRecentPrograms());
    }
    
    @PostMapping("/{id}/complete")
    @Operation(summary = "프로그램 완료", description = "프로그램 완료 기록")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProgramCompletion>> completeProgram(
            @PathVariable Long id,
            @RequestBody ProgramCompletion completion) {
        completion.setProgramId(id);
        return ResponseEntity.ok(programService.completeProgram(completion));
    }
    
    @GetMapping("/completions/patient/{patientId}")
    @Operation(summary = "환자 완료 기록", description = "특정 환자의 프로그램 완료 기록 조회")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ProgramCompletion>>> getCompletionsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(programService.getCompletionsByPatient(patientId));
    }
    
    @GetMapping("/completions/program/{programId}")
    @Operation(summary = "프로그램 완료 기록", description = "특정 프로그램의 완료 기록 조회")
    @PreAuthorize("hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ProgramCompletion>>> getCompletionsByProgram(@PathVariable Long programId) {
        return ResponseEntity.ok(programService.getCompletionsByProgram(programId));
    }
    
    @PostMapping("/{id}/like")
    @Operation(summary = "프로그램 좋아요", description = "프로그램에 좋아요 추가")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> likeProgram(@PathVariable Long id) {
        return ResponseEntity.ok(programService.likeProgram(id));
    }
    
    @DeleteMapping("/{id}/like")
    @Operation(summary = "프로그램 좋아요 취소", description = "프로그램 좋아요 취소")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> unlikeProgram(@PathVariable Long id) {
        return ResponseEntity.ok(programService.unlikeProgram(id));
    }
}
