package org.example.autoreview.domain.bookmark.TILBookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.bookmark.TILBookmark.entity.TILBookmark;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TILBookmarkService {

    private final TILBookmarkCommand tilBookmarkCommand;

    public Long saveOrUpdate(String email, Long postId) {
        try {
            return tilBookmarkCommand.save(email, postId);
        } catch (DataIntegrityViolationException e) {
            return tilBookmarkCommand.update(email, postId);
        }
    }

    public TILBookmark findById(String email, Long postId) {
        return tilBookmarkCommand.findById(email, postId);
    }
}
