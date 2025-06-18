package org.example.autoreview.domain.bookmark.CodePostBookmark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.request.CodePostBookmarkSaveRequestDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.dto.response.CodePostBookmarkListResponseDto;
import org.example.autoreview.domain.bookmark.CodePostBookmark.service.CodePostBookmarkService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Code Post Bookmark API", description = "Code Post Bookmark API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/post/code/bookmark")
public class CodePostBookmarkController {

    private final CodePostBookmarkService codePostBookmarkService;

    @Operation(summary = "북마크 저장 또는 수정", description = "soft delete 되었다면 수정, 존재하지 않는다면 저장")
    @PutMapping
    public ResponseEntity<Long> saveOrUpdate(@RequestBody CodePostBookmarkSaveRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(codePostBookmarkService.saveOrUpdate(requestDto, userDetails.getUsername()));
    }

    @Operation(summary = "북마크 전체 조회")
    @GetMapping("/list")
    public ResponseEntity<CodePostBookmarkListResponseDto> findAll(@AuthenticationPrincipal UserDetails userDetails,
                                                                   @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok().body(codePostBookmarkService.findAllByEmail(userDetails.getUsername(), pageable));
    }

    @Operation(summary = "북마크 삭제")
    @DeleteMapping
    public ResponseEntity<String> deleteExpiredSoftDeletedBookmarks() {
        codePostBookmarkService.deleteExpiredSoftDeletedBookmarks();
        return ResponseEntity.ok().body("Success Hard Delete");
    }

}
