package org.example.autoreview.domain.tilpost.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.bookmark.TILBookmark.dto.TILBookmarkRequestDto;
import org.example.autoreview.domain.tilpost.dto.request.TILPostSaveRequestDto;
import org.example.autoreview.domain.tilpost.dto.request.TILPostUpdateRequestDto;
import org.example.autoreview.domain.tilpost.dto.response.TILBookmarkPostResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostResponseDto;
import org.example.autoreview.domain.tilpost.service.TILPostDtoService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


@RequiredArgsConstructor
@RequestMapping("/v1/api/post/til")
@RestController
public class TILPostApiController {

    private final TILPostDtoService tilPostDtoService;

    @Operation(summary = "TIL 게시물 전체 조회", description = "전체 조회")
    @GetMapping("/list")
    public ResponseEntity<TILPageResponseDto> findAll(@RequestParam(defaultValue = "0", required = false) int page,
                                                      @RequestParam(defaultValue = "10", required = false) int size){

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(tilPostDtoService.findPostAllByPage(pageable));
    }

    @Operation(summary = "본인 TIL 게시물 조회", description = "멤버별 조회")
    @GetMapping("/own")
    public ResponseEntity<TILPageResponseDto> findByMember(@RequestParam(defaultValue = "0", required = false) int page,
                                                        @RequestParam(defaultValue = "10", required = false) int size,
                                                        @AuthenticationPrincipal UserDetails userDetails){

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(tilPostDtoService.findPostByMember(userDetails.getUsername(), pageable));
    }

    @Operation(summary = "본인 TIL 게시물 검색", description = "멤버별 검색")
    @GetMapping("/own/search")
    public ResponseEntity<TILPageResponseDto> findByMemberTitleContains(@RequestParam(defaultValue = "0", required = false) int page,
                                                                     @RequestParam(defaultValue = "10", required = false) int size,
                                                                     @RequestParam(required = false) String keyword,
                                                                     @AuthenticationPrincipal UserDetails userDetails){

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(tilPostDtoService.findPostByMemberTitleContains(userDetails.getUsername(), keyword, pageable));
    }

    @Operation(summary = "TIL 게시물 검색", description = "검색")
    @GetMapping("/search")
    public ResponseEntity<TILPageResponseDto> findByTitleContains(@RequestParam(defaultValue = "0", required = false) int page,
                                                               @RequestParam(defaultValue = "10", required = false) int size,
                                                               @RequestParam(required = false) String keyword){

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(tilPostDtoService.findPostByTitleContains(keyword, pageable));
    }

//    @GetMapping("/cursor")
//    public ResponseEntity<TILCursorResponseDto> findAllByCursorId(@RequestParam(required = false) Long cursorId,
//                                                               @RequestParam int pageSize){
//
//        return ResponseEntity.ok().body(tilPostService.findAllByIdCursorBased(cursorId, pageSize));
//    }

    @Operation(summary = "특정 TIL 게시물 조회", description = "개별 조회")
    @GetMapping("/detail/{id}")
    public ResponseEntity<TILPostResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(tilPostDtoService.findPostById(id));
    }

    @Operation(summary = "로그인된 유저의 특정 TIL 게시물 조회", description = "개별 조회")
    @GetMapping("/own/{id}")
    public ResponseEntity<TILBookmarkPostResponseDto> findByIdLoginIn(@PathVariable Long id,
                                                                   @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok().body(tilPostDtoService.findBookmarkPostById(userDetails.getUsername(), id));
    }

    @Operation(summary = "TIL 게시물 생성", description = "토큰을 통해 유저 선택")
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody TILPostSaveRequestDto saveRequestDto,
                                  @AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok().body(
                tilPostDtoService.postSave(saveRequestDto, userDetails.getUsername()));
    }

    @Operation(summary = "TIL 게시물 갱신", description = "토큰을 통해 유저 식별")
    @PutMapping
    public ResponseEntity<Long> update(@RequestBody TILPostUpdateRequestDto requestDto,
                                    @AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok().body(tilPostDtoService.postUpdate(requestDto, userDetails.getUsername()));
    }

    //나중에 다시 볼 것
//    @Operation(summary = "TIL 게시물 북마크 확인", description = "유저가 북마크했는지 확인")
//    @GetMapping("/bookmark/{postId}")
//    public ResponseEntity<?> getBookmark(@PathVariable Long postId,
//                                   @AuthenticationPrincipal UserDetails userDetails){
//        return ResponseEntity.ok().body(tilPostDtoService.findBookmark(userDetails.getUsername(), postId));
//    }

    @Operation(summary = "TIL 게시물 북마크 생성", description = "유저 북마크 생성")
    @PostMapping("/bookmark")
    public ResponseEntity<?> bookmark(@RequestBody TILBookmarkRequestDto requestDto,
                                   @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok().body(tilPostDtoService.bookmarkPost(userDetails.getUsername(), requestDto.getPostId()));
    }

    @Operation(summary = "TIL 게시물 삭제", description = "토큰을 통해 유저 식별")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok().body(tilPostDtoService.postDelete(id, userDetails.getUsername()));
    }

    @Operation(summary = "TIL 게시물 북마크 전체 삭제", description = "유저 북마크 false 전체 삭제")
    @DeleteMapping("/bookmark")
    public ResponseEntity<?> deleteUselessBookmark(){
        tilPostDtoService.deleteUselessPost();
        return ResponseEntity.ok().body("deleted");
    }
}
