package org.example.autoreview.domain.bookmark.TILBookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.bookmark.TILBookmark.entity.TILBookmark;
import org.example.autoreview.domain.bookmark.TILBookmark.entity.TILBookmarkId;
import org.example.autoreview.domain.bookmark.TILBookmark.entity.TILBookmarkRepository;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.example.autoreview.global.exception.sub_exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TILBookmarkService {

    private final TILBookmarkRepository tilBookmarkRepository;

    @Transactional
    public void save(String email, Long postId){
        TILBookmark id = new TILBookmark(email, postId, true);
        tilBookmarkRepository.save(id);
    }

    public TILBookmark findById(String email, Long postId){
        TILBookmarkId id = new TILBookmarkId(email, postId);
        return tilBookmarkRepository.findById(id).orElseThrow(()
                -> new NotFoundException(ErrorCode.NOT_FOUND_BOOKMARK));
    }

    public List<Long> findPostIdByMemberEmail(String email){
        return tilBookmarkRepository.findTILBookmarksByEmail(email).stream()
                .map(TILBookmark::getPostId)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(String email, Long postId){
        TILBookmarkId id = new TILBookmarkId(email, postId);
        TILBookmark tilBookmark = tilBookmarkRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("can't find bookmark"));

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
