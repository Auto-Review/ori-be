package org.example.autoreview.domain.tilpost.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.bookmark.TILBookmark.entity.TILBookmark;
import org.example.autoreview.domain.bookmark.TILBookmark.service.TILBookmarkService;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.example.autoreview.domain.member.service.MemberService;
import org.example.autoreview.domain.tilpost.dto.request.TILPostSaveRequestDto;
import org.example.autoreview.domain.tilpost.dto.request.TILPostUpdateRequestDto;
import org.example.autoreview.domain.tilpost.dto.response.TILBookmarkPostResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPageResponseDto;
import org.example.autoreview.domain.tilpost.dto.response.TILPostResponseDto;
import org.example.autoreview.domain.tilpost.entity.TILPost;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class TILPostDtoService {

    private final MemberService memberService;
    private final TILPostService tilPostService;
    private final TILBookmarkService tilBookmarkService;
    private final MemberCommand memberCommand;

    public Long postSave(TILPostSaveRequestDto requestDto, String email){
        Member member = memberService.findByEmail(email);
        return tilPostService.save(requestDto, member);
    }

//    public Long bookmarkPost(String email, Long postId){
//        try{
//            tilPostService.findEntityById(postId);
//            tilBookmarkService.update(email, postId);
//        } catch (NoSuchElementException e){
//            log.info("create bookmark");
//            tilBookmarkService.save(email, postId);
//        }
//        return postId;
//    }

    // 나중에 다시 볼 것
//    public TILBookmarkResponseDto findBookmark(String email, Long postId){
//        return tilBookmarkService.findById(email, postId).map(TILBookmarkResponseDto::new).orElseThrow(()
//                -> new NotFoundException(ErrorCode.NOT_FOUND_BOOKMARK));
//    }

//    public void deleteUselessPost(){
//        tilBookmarkService.deleteUseless();
//    }

    public TILPageResponseDto findPostByMember(String email, Pageable pageable){
        Member member = memberService.findByEmail(email);
        return tilPostService.findByMember(member, pageable);
    }

    public TILPageResponseDto findPostByMemberTitleContains(String email, String keyword, Pageable pageable){
        Member member = memberService.findByEmail(email);
        return tilPostService.findByMemberTitleContains(member, keyword, pageable);
    }

    public TILPageResponseDto findPostAllByPage(Pageable pageable){
        return tilPostService.findAllByPage(pageable);
    }

    public TILPageResponseDto findPostByTitleContains(String keyword, Pageable pageable){
        return tilPostService.findByTitleContains(keyword, pageable);
    }

    public TILPostResponseDto findPostById(Long id){
        return tilPostService.findById(id);
    }

//    public TILBookmarkPostResponseDto findBookmarkPostById(String email, Long postId){
//        Optional<TILBookmark> bookmark = tilBookmarkService.findById(email, postId);
//        TILPost post = tilPostService.findEntityById(postId);
//        Member member = memberCommand.findById(post.getWriterId());
//        return bookmark.map(tilBookmark ->
//                new TILBookmarkPostResponseDto(post, tilBookmark.getIsBookmarked(), member)).orElseGet(() ->
//                new TILBookmarkPostResponseDto(post, null, member));
//    }

    public Long postUpdate(TILPostUpdateRequestDto requestDto, String email){
        return tilPostService.update(requestDto, email);
    }

    public Long postDelete(Long id, String email){
        return tilPostService.delete(id, email);
    }

}
