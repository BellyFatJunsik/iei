package kr.co.iei.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String role; // PATIENT, THERAPIST, ADMIN
    private String profileImage;
    private String status; // ACTIVE, INACTIVE, PENDING
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 물리치료사 전용 필드
    private String licenseNumber;
    private String licenseImage;
    private String hospitalName;
    private String hospitalAddress;
    private String introduction;
    private String specialties; // 전문 분야 (JSON 형태로 저장)
    private Integer experience; // 경력 년수
    private String education; // 학력
    private String certifications; // 자격증 (JSON 형태로 저장)
    
    // 환자 전용 필드
    private String birthDate;
    private String gender;
    private String address;
    private String emergencyContact;
    private String medicalHistory; // 병력 (JSON 형태로 저장)
    private String currentCondition; // 현재 상태
    private String treatmentGoal; // 치료 목표
}
