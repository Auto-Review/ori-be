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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TILBookmarkService {

    private final TILBookmarkRepository tilBookmarkRepository;

    @Transactional
    public void save(String email, Long postId){
        TILBookmark id = new TILBookmark(email, postId);
        tilBookmarkRepository.save(id);
    }

    public TILBookmark findById(String email, Long postId){
        TILBookmarkId id = new TILBookmarkId(email, postId);
        TILBookmark tilBookmark = tilBookmarkRepository.findById(id).orElseThrow(()
                -> new NotFoundException(ErrorCode.NOT_FOUND_BOOKMARK));
        return tilBookmark;
    }

    public List<Long> findPostIdByMemberEmail(String email){
        return tilBookmarkRepository.findTILBookmarksByEmail(email).stream()
                .map(TILBookmark::getPostId)
                .collect(Collectors.toList());
    }
}
