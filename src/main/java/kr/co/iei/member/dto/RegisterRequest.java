package kr.co.iei.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "아이디는 필수입니다")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 4-20자의 영문자와 숫자만 사용 가능합니다")
    private String username;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", 
             message = "비밀번호는 8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다")
    private String password;
    
    @NotBlank(message = "이름은 필수입니다")
    private String name;
    
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
    
    @NotBlank(message = "전화번호는 필수입니다")
    @Pattern(regexp = "^01[0-9]-\\d{4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다 (010-1234-5678)")
    private String phone;
    
    @NotBlank(message = "역할은 필수입니다")
    @Pattern(regexp = "^(PATIENT|THERAPIST)$", message = "역할은 PATIENT 또는 THERAPIST여야 합니다")
    private String role;
    
    // 물리치료사 전용 필드
    private String licenseNumber;
    private String hospitalName;
    private String hospitalAddress;
    private String introduction;
    private String specialties;
    private Integer experience;
    private String education;
    private String certifications;
    
    // 환자 전용 필드
    private String birthDate;
    private String gender;
    private String address;
    private String emergencyContact;
    private String medicalHistory;
    private String currentCondition;
    private String treatmentGoal;
}
