package kr.co.iei.review.service;

import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.review.dao.ReviewDao;
import kr.co.iei.review.dto.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewDao reviewDao;
    
    public ApiResponse<Review> createReview(Review review) {
        try {
            int result = reviewDao.insertReview(review);
            if (result > 0) {
                return ApiResponse.success("후기가 등록되었습니다.", review);
            } else {
                return ApiResponse.<Review>error("후기 등록에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("후기 등록 중 오류 발생", e);
            return ApiResponse.<Review>error("후기 등록 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Review>> getReviewsByProgram(Long programId) {
        try {
            List<Review> reviews = reviewDao.selectReviewsByProgram(programId);
            return ApiResponse.success(reviews);
        } catch (Exception e) {
            log.error("프로그램 후기 조회 중 오류 발생", e);
            return ApiResponse.<List<Review>>error("프로그램 후기 조회 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<Review> updateReview(Long id, Review review) {
        try {
            review.setId(id);
            int result = reviewDao.updateReview(review);
            if (result > 0) {
                return ApiResponse.success("후기가 수정되었습니다.", review);
            } else {
                return ApiResponse.<Review>error("후기 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("후기 수정 중 오류 발생", e);
            return ApiResponse.<Review>error("후기 수정 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<Void> deleteReview(Long id) {
        try {
            int result = reviewDao.deleteReview(id);
            if (result > 0) {
                return ApiResponse.<Void>success("후기가 삭제되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("후기 삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("후기 삭제 중 오류 발생", e);
            return ApiResponse.<Void>error("후기 삭제 중 오류가 발생했습니다.");
        }
    }
}
