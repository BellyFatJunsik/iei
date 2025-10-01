package kr.co.iei.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.member.dto.*;
import kr.co.iei.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "회원 인증 관련 API")
public class MemberController {
    
    private final MemberService memberService;
    
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 로그인")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(memberService.login(request));
    }
    
    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "새로운 사용자 회원가입")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(memberService.register(request));
    }
    
    @GetMapping("/profile")
    @Operation(summary = "프로필 조회", description = "현재 사용자의 프로필 정보 조회")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Member>> getProfile(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(memberService.getMemberProfile(username));
    }
    
    @PutMapping("/profile")
    @Operation(summary = "프로필 수정", description = "현재 사용자의 프로필 정보 수정")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateProfile(
            Authentication authentication,
            @Valid @RequestBody Member updateData) {
        String username = authentication.getName();
        return ResponseEntity.ok(memberService.updateProfile(username, updateData));
    }
    
    @PostMapping("/profile/image")
    @Operation(summary = "프로필 이미지 업로드", description = "프로필 이미지 업로드")
    @PreAuthorize("hasRole('PATIENT') or hasRole('THERAPIST') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> uploadProfileImage(
            Authentication authentication,
            @RequestParam("file") MultipartFile file) {
        String username = authentication.getName();
        return ResponseEntity.ok(memberService.uploadProfileImage(username, file));
    }
    
    @GetMapping("/therapists/pending")
    @Operation(summary = "승인 대기 물리치료사 목록", description = "승인 대기 중인 물리치료사 목록 조회")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Member>>> getPendingTherapists() {
        return ResponseEntity.ok(memberService.getPendingTherapists());
    }
    
    @PostMapping("/therapists/{id}/approve")
    @Operation(summary = "물리치료사 승인", description = "물리치료사 등록 승인")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> approveTherapist(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.approveTherapist(id));
    }
    
    @PostMapping("/therapists/{id}/reject")
    @Operation(summary = "물리치료사 거부", description = "물리치료사 등록 거부")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> rejectTherapist(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.rejectTherapist(id));
    }
    
    @GetMapping("/members")
    @Operation(summary = "회원 목록 조회", description = "역할별 회원 목록 조회")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Member>>> getMembersByRole(
            @RequestParam String role,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(memberService.getMembersByRole(role, page, size));
    }
}
