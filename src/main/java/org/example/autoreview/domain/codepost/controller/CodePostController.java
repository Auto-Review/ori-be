package org.example.autoreview.domain.codepost.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.codepost.dto.request.CodePostSaveRequestDto;
import org.example.autoreview.domain.codepost.dto.request.CodePostUpdateRequestDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostListResponseDto;
import org.example.autoreview.domain.codepost.dto.response.CodePostResponseDto;
import org.example.autoreview.domain.codepost.service.CodePostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "코드 포스트 API", description = "코드 포스트 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api/post/code")
public class CodePostController {

    private final CodePostService codePostService;

    @Operation(summary = "코드 포스트 생성", description = "코드 포스트 생성")
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody CodePostSaveRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(codePostService.save(requestDto, userDetails.getUsername()));
    }

    @Operation(summary = "제목으로 코드 포스트 검색", description = "공백 또는 null 입력 시 에러 반환")
    @GetMapping("/search")
    public ResponseEntity<CodePostListResponseDto> search(@RequestParam String keyword,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "9") int size,
                                                          @RequestParam(defaultValue = "desc") String direction,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "all") String language) {
        return ResponseEntity.ok().body(codePostService.search(keyword, page,size,direction,sortBy,language));
    }

    @Operation(summary = "코드 포스트 단일 조회", description = "공개된 포스트 or 작성자일 경우만 조회됨 + 북마크 여부 포함")
    @GetMapping("/detail/{id}")
    public ResponseEntity<CodePostResponseDto> view(@PathVariable("id") Long codePostId,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(codePostService.findById(codePostId,userDetails.getUsername()));
    }

    @Operation(summary = "코드 포스트 전체 조회", description = "코드 포스트 전체 조회(비공개 포스트는 제외)")
    @GetMapping("/list")
    public ResponseEntity<CodePostListResponseDto> viewAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "9") int size,
                                                           @RequestParam(defaultValue = "desc") String direction,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "all") String language) {
        return ResponseEntity.ok().body(codePostService.findPostByPage(page,size,direction,sortBy,language));
    }

    @Operation(summary = "내가 쓴 코드 포스트 조회", description = "내가 쓴 코드 포스트 조회")
    @GetMapping("/own")
    public ResponseEntity<CodePostListResponseDto> myCodePostPage(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "9") int size,
                                                                  @RequestParam(defaultValue = "desc") String direction,
                                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                                  @RequestParam(defaultValue = "all") String language) {
        return ResponseEntity.ok().body(codePostService.findByMemberId(userDetails.getUsername(),page,size,direction,sortBy,language));
    }

    @Operation(summary = "내 코드 포스트 검색", description = "내 코드 포스트 검색")
    @GetMapping("/own/search")
    public ResponseEntity<CodePostListResponseDto> mySearch(@RequestParam String keyword,
                                                            @AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "9") int size,
                                                            @RequestParam(defaultValue = "desc") String direction,
                                                            @RequestParam(defaultValue = "id") String sortBy,
                                                            @RequestParam(defaultValue = "all") String language) {
        return ResponseEntity.ok().body(codePostService.mySearch(keyword, userDetails.getUsername(), page,size,direction,sortBy,language));
    }

    @Operation(summary = "코드 포스트 수정", description = "코드 포스트 수정")
    @PutMapping
    public ResponseEntity<Long> update(@RequestBody CodePostUpdateRequestDto requestDto,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(codePostService.update(requestDto, userDetails.getUsername()));
    }

    @Operation(summary = "코드 포스트 삭제", description = "코드 포스트 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long codePostId,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(codePostService.delete(codePostId, userDetails.getUsername()));
    }

}
