package kr.co.iei.member.service;

import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.common.util.JwtUtil;
import kr.co.iei.common.util.PageInfoUtil;
import kr.co.iei.common.dto.PageInfo;
import kr.co.iei.member.dao.MemberDao;
import kr.co.iei.member.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @Transactional
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        try {
            Optional<Member> memberOpt = memberDao.selectMemberByUsername(request.getUsername());
            
            if (memberOpt.isEmpty()) {
                return ApiResponse.<LoginResponse>error("아이디 또는 비밀번호가 올바르지 않습니다.");
            }
            
            Member member = memberOpt.get();
            
            if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
                return ApiResponse.<LoginResponse>error("아이디 또는 비밀번호가 올바르지 않습니다.");
            }
            
            if (!"ACTIVE".equals(member.getStatus())) {
                return ApiResponse.<LoginResponse>error("비활성화된 계정입니다. 관리자에게 문의하세요.");
            }
            
            String token = jwtUtil.generateToken(member.getUsername(), member.getRole());
            
            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .username(member.getUsername())
                    .name(member.getName())
                    .role(member.getRole())
                    .profileImage(member.getProfileImage())
                    .expiresIn(86400000L) // 24시간
                    .build();
            
            return ApiResponse.success("로그인 성공", response);
            
        } catch (Exception e) {
            log.error("로그인 처리 중 오류 발생", e);
            return ApiResponse.<LoginResponse>error("로그인 처리 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Void> register(RegisterRequest request) {
        try {
            // 아이디 중복 확인
            if (memberDao.existsByUsername(request.getUsername())) {
                return ApiResponse.<Void>error("이미 사용 중인 아이디입니다.");
            }
            
            // 이메일 중복 확인
            if (memberDao.existsByEmail(request.getEmail())) {
                return ApiResponse.<Void>error("이미 사용 중인 이메일입니다.");
            }
            
            Member member = Member.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .role(request.getRole())
                    .status("THERAPIST".equals(request.getRole()) ? "PENDING" : "ACTIVE")
                    .licenseNumber(request.getLicenseNumber())
                    .hospitalName(request.getHospitalName())
                    .hospitalAddress(request.getHospitalAddress())
                    .introduction(request.getIntroduction())
                    .specialties(request.getSpecialties())
                    .experience(request.getExperience())
                    .education(request.getEducation())
                    .certifications(request.getCertifications())
                    .birthDate(request.getBirthDate())
                    .gender(request.getGender())
                    .address(request.getAddress())
                    .emergencyContact(request.getEmergencyContact())
                    .medicalHistory(request.getMedicalHistory())
                    .currentCondition(request.getCurrentCondition())
                    .treatmentGoal(request.getTreatmentGoal())
                    .build();
            
            int result = memberDao.insertMember(member);
            
            if (result > 0) {
                return ApiResponse.<Void>success("회원가입이 완료되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("회원가입에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("회원가입 처리 중 오류 발생", e);
            return ApiResponse.<Void>error("회원가입 처리 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<Member> getMemberProfile(String username) {
        try {
            Optional<Member> memberOpt = memberDao.selectMemberByUsername(username);
            
            if (memberOpt.isEmpty()) {
                return ApiResponse.<Member>error("회원 정보를 찾을 수 없습니다.");
            }
            
            return ApiResponse.success(memberOpt.get());
            
        } catch (Exception e) {
            log.error("회원 정보 조회 중 오류 발생", e);
            return ApiResponse.<Member>error("회원 정보 조회 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Void> updateProfile(String username, Member updateData) {
        try {
            Optional<Member> memberOpt = memberDao.selectMemberByUsername(username);
            
            if (memberOpt.isEmpty()) {
                return ApiResponse.<Void>error("회원 정보를 찾을 수 없습니다.");
            }
            
            Member member = memberOpt.get();
            member.setName(updateData.getName());
            member.setEmail(updateData.getEmail());
            member.setPhone(updateData.getPhone());
            member.setIntroduction(updateData.getIntroduction());
            member.setSpecialties(updateData.getSpecialties());
            member.setEducation(updateData.getEducation());
            member.setCertifications(updateData.getCertifications());
            member.setAddress(updateData.getAddress());
            member.setMedicalHistory(updateData.getMedicalHistory());
            member.setCurrentCondition(updateData.getCurrentCondition());
            member.setTreatmentGoal(updateData.getTreatmentGoal());
            
            int result = memberDao.updateMember(member);
            
            if (result > 0) {
                return ApiResponse.<Void>success("프로필이 수정되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("프로필 수정에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("프로필 수정 중 오류 발생", e);
            return ApiResponse.<Void>error("프로필 수정 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Void> uploadProfileImage(String username, MultipartFile file) {
        try {
            // 파일 업로드 로직은 FileUtil을 사용하여 구현
            // 여기서는 간단히 파일명만 저장하는 예시
            
            Optional<Member> memberOpt = memberDao.selectMemberByUsername(username);
            
            if (memberOpt.isEmpty()) {
                return ApiResponse.<Void>error("회원 정보를 찾을 수 없습니다.");
            }
            
            Member member = memberOpt.get();
            member.setProfileImage(file.getOriginalFilename());
            
            int result = memberDao.updateMember(member);
            
            if (result > 0) {
                return ApiResponse.<Void>success("프로필 이미지가 업로드되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("프로필 이미지 업로드에 실패했습니다.");
            }
            
        } catch (Exception e) {
            log.error("프로필 이미지 업로드 중 오류 발생", e);
            return ApiResponse.<Void>error("프로필 이미지 업로드 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Member>> getPendingTherapists() {
        try {
            List<Member> therapists = memberDao.selectPendingTherapists();
            return ApiResponse.success(therapists);
        } catch (Exception e) {
            log.error("승인 대기 물리치료사 조회 중 오류 발생", e);
            return ApiResponse.<List<Member>>error("승인 대기 물리치료사 조회 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Void> approveTherapist(Long id) {
        try {
            int result = memberDao.approveTherapist(id);
            
            if (result > 0) {
                return ApiResponse.<Void>success("물리치료사가 승인되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("물리치료사 승인에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("물리치료사 승인 중 오류 발생", e);
            return ApiResponse.<Void>error("물리치료사 승인 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public ApiResponse<Void> rejectTherapist(Long id) {
        try {
            int result = memberDao.rejectTherapist(id);
            
            if (result > 0) {
                return ApiResponse.<Void>success("물리치료사 등록이 거부되었습니다.", null);
            } else {
                return ApiResponse.<Void>error("물리치료사 거부에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("물리치료사 거부 중 오류 발생", e);
            return ApiResponse.<Void>error("물리치료사 거부 중 오류가 발생했습니다.");
        }
    }
    
    public ApiResponse<List<Member>> getMembersByRole(String role, int page, int size) {
        try {
            int offset = PageInfoUtil.getOffset(page, size);
            List<Member> members = memberDao.selectMembersByRoleWithPaging(role, offset, size);
            int totalCount = memberDao.countMembersByRole(role);
            PageInfo pageInfo = PageInfoUtil.createPageInfo(page, totalCount, size);
            
            return ApiResponse.success(members);
        } catch (Exception e) {
            log.error("회원 목록 조회 중 오류 발생", e);
            return ApiResponse.<List<Member>>error("회원 목록 조회 중 오류가 발생했습니다.");
        }
    }
}
