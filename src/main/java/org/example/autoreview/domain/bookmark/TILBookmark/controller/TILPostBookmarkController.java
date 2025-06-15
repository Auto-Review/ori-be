package org.example.autoreview.domain.bookmark.TILBookmark.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.bookmark.TILBookmark.dto.TILBookmarkRequestDto;
import org.example.autoreview.domain.bookmark.TILBookmark.service.TILBookmarkService;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostThumbnailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TIL Post Bookmark API", description = "TIL Post Bookmark API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/post/til/bookmark")
public class TILPostBookmarkController {

    private final TILBookmarkService tilBookmarkService;

//    @Operation(summary = "TIL 게시물 북마크 생성", description = "유저 북마크 생성")
//    @PostMapping
//    public ResponseEntity<?> bookmark(@RequestBody TILBookmarkRequestDto requestDto,
//                                      @AuthenticationPrincipal UserDetails userDetails){
//        return ResponseEntity.ok().body(tilBookmarkService.save(userDetails.getUsername(), requestDto.getPostId()));
//    }
//
//    @GetMapping
//    public ResponseEntity<TILPageResponseDto> bookmarkedTIL(@PageableDefault(page = 0, size = 9) Pageable pageable,
//                                                                           @AuthenticationPrincipal UserDetails userDetails) {
//        return ResponseEntity.ok().body(tilBookmarkService.findPostIdByMemberEmail(userDetails.getUsername(), pageable));
//    }
//
//    @Operation(summary = "TIL 북마크 게시물 삭제", description = "토큰을 통해 유저 식별")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Long> delete(@PathVariable Long id,
//                                       @AuthenticationPrincipal UserDetails userDetails){
//
//        return ResponseEntity.ok().body(tilBookmarkService.delete(userDetails.getUsername(), id));
//    }
//
//    @Operation(summary = "TIL 게시물 북마크 전체 삭제", description = "유저 북마크 false 전체 삭제")
//    @DeleteMapping("/bookmark")
//    public ResponseEntity<?> deleteUselessBookmark(){
//        tilBookmarkService.deleteUseless();
//        return ResponseEntity.ok().body("deleted");
//    }

}
