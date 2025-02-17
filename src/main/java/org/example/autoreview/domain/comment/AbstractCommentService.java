package org.example.autoreview.domain.comment;

import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.comment.codepost.dto.request.CommentSaveRequestDto;
import org.example.autoreview.domain.comment.codepost.entity.CodePostComment;
import org.example.autoreview.domain.comment.codepost.entity.CommentRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public abstract class AbstractCommentService<T extends CommentSaveRequestDto, P> implements GenericCommentService<T, P> {

    protected abstract CommentRepository getCommentRepository();
    protected abstract P findPostById(Long postId);
    protected abstract Member findMemberByEmail(String email);
    protected abstract CodePostComment findCommentById(Long commentId);

    @Override
    @Transactional
    public Long save(T requestDto, String email) {
        P post = findPostById(requestDto.postId());
        Member writer = findMemberByEmail(email);

        if (requestDto.getParentId() != null) {
            CodePostComment parent = findCommentById(requestDto.getParentId());
            return getCommentRepository().save(requestDto.toReplyEntity(post, parent, writer)).getId();
        }
        return getCommentRepository().save(requestDto.toCommentEntity(post, writer)).getId();
    }

    // 다른 메서드들도 비슷하게 구현...

    protected void memberValidator(Long writerId, String email) {
        Member writer = findMemberByEmail(email);
        if (!writer.getEmail().equals(email)) {
            throw new CustomRuntimeException(ErrorCode.UNMATCHED_EMAIL);
        }
    }
}

