package org.example.autoreview.domain.comment.codepost.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.comment.base.dto.request.CommentDeleteRequestDto;
import org.example.autoreview.domain.comment.base.dto.request.CommentSaveRequestDto;
import org.example.autoreview.domain.comment.base.dto.request.CommentUpdateRequestDto;
import org.example.autoreview.domain.comment.base.dto.response.CommentListResponseDto;
import org.example.autoreview.domain.comment.codepost.service.CodePostCommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Code Post 댓글 API", description = "댓글 & 대댓글 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/code-post")
public class CodePostCommentController {

    private final CodePostCommentService codePostCommentService;

    @Operation(summary = "댓글 또는 대댓글 생성", description = "parent_id 가 있으면 대댓글, 없으면 댓글")
    @PostMapping("/comment")
    public ResponseEntity<Long> save(@RequestBody CommentSaveRequestDto requestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(codePostCommentService.save(requestDto,userDetails.getUsername()));
    }

    @Operation(summary = "댓글 조회", description = "페이지네이션 적용, limit 6개")
    @GetMapping("/{codePostId}/comments")
    public ResponseEntity<CommentListResponseDto> getComments(@PageableDefault(page = 0, size = 6) Pageable pageable,
                                                              @PathVariable Long codePostId) {
        return ResponseEntity.ok().body(codePostCommentService.findByCommentPage(codePostId,pageable));
    }

    @Operation(summary = "대댓글 조회", description = "페이지네이션 적용, limit 3개, parent_id 있어야 함")
    @GetMapping("/code-post/{codePostId}/replies")
    public ResponseEntity<CommentListResponseDto> getReplies(@PageableDefault(page = 0, size = 3) Pageable pageable,
                                                             @RequestParam Long parentId, @PathVariable Long codePostId) {
        return ResponseEntity.ok().body(codePostCommentService.findByReplyPage(codePostId, parentId, pageable));
    }

    @Operation(summary = "댓글 또는 대댓글 수정", description = "parent_id가 있으면 대댓글, 없으면 댓글")
    @PutMapping("/comment")
    public ResponseEntity<Long> update(@RequestBody CommentUpdateRequestDto requestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(codePostCommentService.update(requestDto, userDetails.getUsername()));
    }

    @Operation(summary = "댓글 또는 대댓글 삭제", description = "parent_id가 있으면 대댓글, 없으면 댓글")
    @DeleteMapping("/comment")
    public ResponseEntity<Long> delete(@RequestBody CommentDeleteRequestDto requestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(codePostCommentService.delete(requestDto, userDetails.getUsername()));
    }
}
