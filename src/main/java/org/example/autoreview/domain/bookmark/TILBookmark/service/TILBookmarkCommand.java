package org.example.autoreview.domain.bookmark.TILBookmark.service;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.bookmark.TILBookmark.entity.TILBookmark;
import org.example.autoreview.domain.bookmark.TILBookmark.entity.TILBookmarkId;
import org.example.autoreview.domain.bookmark.TILBookmark.entity.TILBookmarkRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TILBookmarkCommand {

    private final TILBookmarkRepository tilBookmarkRepository;

    @Transactional
    public Long save(String email, Long postId) {
        return tilBookmarkRepository.save(new TILBookmark(email, postId, true)).getPostId();
    }

    public TILBookmark findById(String email, Long postId){
        TILBookmarkId id = new TILBookmarkId(email, postId);
        return tilBookmarkRepository.findById(id).orElseThrow(()
                -> new NotFoundException(ErrorCode.NOT_FOUND_BOOKMARK));
    }

    public List<Long> findPostIdByMemberEmail(String email, Pageable pageable){
        return tilBookmarkRepository.findTILBookmarksByEmailAndIsBookmarked(email, true, pageable).stream()
                .map(TILBookmark::getPostId)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(String email, Long postId){
        TILBookmarkId id = new TILBookmarkId(email, postId);
        TILBookmark tilBookmark = tilBookmarkRepository.findById(id).orElseThrow(()
                -> new NotFoundException(ErrorCode.NOT_FOUND_BOOKMARK));

        tilBookmark.update(tilBookmark.getIsBookmarked());
        return postId;
    }

    @Transactional
    public Long delete(String email, Long postId){
        TILBookmarkId id = new TILBookmarkId(email, postId);
        TILBookmark tilBookmark = tilBookmarkRepository.findById(id).orElseThrow(()
                -> new NotFoundException(ErrorCode.NOT_FOUND_BOOKMARK));
        tilBookmarkRepository.delete(tilBookmark);
        return postId;
    }

    @Transactional
    public void deleteUseless(){
        List<TILBookmark> list = tilBookmarkRepository.findTILBookmarksByIsBookmarked(false);
        tilBookmarkRepository.deleteAll(list);
    }
}
