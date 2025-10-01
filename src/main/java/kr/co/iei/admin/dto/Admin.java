package kr.co.iei.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String role; // ADMIN
    private String status; // ACTIVE, INACTIVE
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String lastLoginAt;
    private String permissions; // 권한 (JSON 형태로 저장)
}
