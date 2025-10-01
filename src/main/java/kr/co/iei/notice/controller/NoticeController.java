package kr.co.iei.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.iei.common.response.ApiResponse;
import kr.co.iei.notice.dto.Notice;
import kr.co.iei.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
@Tag(name = "공지사항", description = "공지사항 관리 API")
public class NoticeController {
    
    private final NoticeService noticeService;
    
    @PostMapping
    @Operation(summary = "공지사항 등록", description = "새로운 공지사항 등록")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Notice>> createNotice(@RequestBody Notice notice) {
        return ResponseEntity.ok(noticeService.createNotice(notice));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "공지사항 조회", description = "특정 공지사항 상세 조회")
    public ResponseEntity<ApiResponse<Notice>> getNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.getNotice(id));
    }
    
    @GetMapping
    @Operation(summary = "공지사항 목록", description = "전체 공지사항 목록 조회")
    public ResponseEntity<ApiResponse<List<Notice>>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "공지사항 수정", description = "기존 공지사항 수정")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Notice>> updateNotice(@PathVariable Long id, @RequestBody Notice notice) {
        return ResponseEntity.ok(noticeService.updateNotice(id, notice));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "공지사항 삭제", description = "공지사항 삭제")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.deleteNotice(id));
    }
}
