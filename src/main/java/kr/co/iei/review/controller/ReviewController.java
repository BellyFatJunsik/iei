package kr.co.iei.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.review.dto.Review;
import kr.co.iei.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Tag(name = "후기", description = "후기 및 댓글 관리 API")
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @PostMapping
    @Operation(summary = "후기 작성", description = "새로운 후기 작성")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Review>> createReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.createReview(review));
    }
    
    @GetMapping("/program/{programId}")
    @Operation(summary = "프로그램 후기 조회", description = "특정 프로그램의 후기 목록 조회")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsByProgram(@PathVariable Long programId) {
        return ResponseEntity.ok(reviewService.getReviewsByProgram(programId));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "후기 수정", description = "기존 후기 수정")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Review>> updateReview(@PathVariable Long id, @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "후기 삭제", description = "후기 삭제")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.deleteReview(id));
    }
}
