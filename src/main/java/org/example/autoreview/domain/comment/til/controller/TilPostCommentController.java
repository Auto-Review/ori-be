package org.example.autoreview.domain.comment.til.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.comment.base.dto.request.CommentDeleteRequestDto;
import org.example.autoreview.domain.comment.base.dto.request.CommentSaveRequestDto;
import org.example.autoreview.domain.comment.base.dto.request.CommentUpdateRequestDto;
import org.example.autoreview.domain.comment.base.dto.response.CommentListResponseDto;
import org.example.autoreview.domain.comment.til.service.TilPostCommentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TIL Post 댓글 API", description = "댓글 & 대댓글 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/til-post")
public class TilPostCommentController {

    private final TilPostCommentService tilPostCommentService;

    @Operation(summary = "댓글 또는 대댓글 생성", description = "parent_id 가 있으면 대댓글, 없으면 댓글")
    @PostMapping("/comment")
    public ResponseEntity<Long> save(@RequestBody CommentSaveRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(tilPostCommentService.save(requestDto,userDetails.getUsername()));
    }

    @Operation(summary = "USER 댓글 조회", description = "USER 전용 전용 조회 api, 페이지네이션 적용, limit 6개")
    @GetMapping("/{tilPostId}/USER/comments")
    public ResponseEntity<CommentListResponseDto> getComments(@PageableDefault(page = 0, size = 6) Pageable pageable,
                                                              @AuthenticationPrincipal UserDetails userDetails,
                                                              @PathVariable Long tilPostId) {
        return ResponseEntity.ok().body(tilPostCommentService.userFindCommentPage(tilPostId,pageable,userDetails.getUsername()));
    }

    @Operation(summary = "USER 대댓글 조회", description = "USER 전용 조회 api, 페이지네이션 적용, limit 3개, parent_id 있어야 함")
    @GetMapping("/{tilPostId}/USER/replies")
    public ResponseEntity<CommentListResponseDto> getReplies(@PageableDefault(page = 0, size = 3) Pageable pageable,
                                                             @AuthenticationPrincipal UserDetails userDetails,
                                                             @RequestParam Long parentId, @PathVariable Long tilPostId) {
        return ResponseEntity.ok().body(tilPostCommentService.userFindReplyPage(tilPostId, parentId, pageable, userDetails.getUsername()));
    }

    @Operation(summary = "GUEST 댓글 조회", description = "GUEST 전용 조회 api, 페이지네이션 적용, limit 6개")
    @GetMapping("/{tilPostId}/GUEST/comments")
    public ResponseEntity<CommentListResponseDto> getComments(@PageableDefault(page = 0, size = 6) Pageable pageable,
                                                              @PathVariable Long tilPostId) {
        return ResponseEntity.ok().body(tilPostCommentService.guestFindCommentPage(tilPostId,pageable));
    }

    @Operation(summary = "GUEST 대댓글 조회", description = "GUEST 전용 조회 api, 페이지네이션 적용, limit 3개, parent_id 있어야 함")
    @GetMapping("/{tilPostId}/GUEST/replies")
    public ResponseEntity<CommentListResponseDto> getReplies(@PageableDefault(page = 0, size = 3) Pageable pageable,
                                                             @RequestParam Long parentId, @PathVariable Long tilPostId) {
        return ResponseEntity.ok().body(tilPostCommentService.guestFindReplyPage(tilPostId, parentId, pageable));
    }

    @Operation(summary = "댓글 또는 대댓글 수정", description = "parent_id가 있으면 대댓글, 없으면 댓글")
    @PutMapping("/comment")
    public ResponseEntity<Long> update(@RequestBody CommentUpdateRequestDto requestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(tilPostCommentService.update(requestDto, userDetails.getUsername()));
    }

    @Operation(summary = "댓글 또는 대댓글 삭제", description = "parent_id가 있으면 대댓글, 없으면 댓글")
    @DeleteMapping("/comment")
    public ResponseEntity<Long> delete(@RequestBody CommentDeleteRequestDto requestDto,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(tilPostCommentService.delete(requestDto, userDetails.getUsername()));
    }
}

