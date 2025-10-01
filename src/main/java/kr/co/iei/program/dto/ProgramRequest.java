package kr.co.iei.program.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramRequest {
    @NotBlank(message = "프로그램명은 필수입니다")
    private String name;
    
    @NotBlank(message = "설명은 필수입니다")
    private String description;
    
    @NotBlank(message = "카테고리는 필수입니다")
    private String category;
    
    @NotBlank(message = "난이도는 필수입니다")
    private String difficulty;
    
    @NotNull(message = "운동 시간은 필수입니다")
    private Integer duration;
    
    private String targetMuscles;
    private String equipment;
    private String instructions;
    private String videoUrl;
    private String thumbnailUrl;
    private String aiAnalysis;
    private String recommendedFor;
    private String contraindications;
    private List<ExerciseStepRequest> exerciseSteps;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ExerciseStepRequest {
    private Integer stepNumber;
    private String stepName;
    private String description;
    private String imageUrl;
    private String videoUrl;
    private Integer duration;
    private Integer repetitions;
    private String notes;
}
