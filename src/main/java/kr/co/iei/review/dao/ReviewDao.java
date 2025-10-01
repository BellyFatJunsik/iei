package kr.co.iei.review.dao;

import kr.co.iei.review.dto.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewDao {
    int insertReview(Review review);
    Review selectReviewById(Long id);
    List<Review> selectReviewsByProgram(Long programId);
    List<Review> selectReviewsByPatient(Long patientId);
    List<Review> selectReviewsByTherapist(Long therapistId);
    int updateReview(Review review);
    int deleteReview(Long id);
    int countReviewsByProgram(Long programId);
    double getAverageRatingByProgram(Long programId);
}
