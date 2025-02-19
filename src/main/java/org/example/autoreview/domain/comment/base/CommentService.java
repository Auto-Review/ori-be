package org.example.autoreview.domain.comment.base;

import lombok.RequiredArgsConstructor;
import org.example.autoreview.domain.comment.base.dto.request.CommentDeleteRequestDto;
import org.example.autoreview.domain.comment.base.dto.request.CommentSaveRequestDto;
import org.example.autoreview.domain.comment.base.dto.request.CommentUpdateRequestDto;
import org.example.autoreview.domain.comment.base.dto.response.CommentListResponseDto;
import org.example.autoreview.domain.comment.base.dto.response.CommentResponseDto;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class CommentService<C extends Comment, R extends CommentRepository<C>> {

    protected final R commentRepository;
    protected final CommentCommand<C,R> commentCommand;
    protected final MemberCommand memberCommand;

    @Transactional
    public Long save(CommentSaveRequestDto requestDto, String email) {
        Member writer = memberCommand.findByEmail(email);

        if (requestDto.parentId() != null) {
            C parent = commentCommand.findById(requestDto.parentId());
            return commentRepository.save(createReplyEntity(requestDto, parent, writer)).getId();
        }
        return commentRepository.save(createCommentEntity(requestDto, writer)).getId();
    }

    protected abstract C createReplyEntity(CommentSaveRequestDto requestDto, C parent, Member writer);
    protected abstract C createCommentEntity(CommentSaveRequestDto requestDto, Member writer);

    // 상위 댓글 조회
    public CommentListResponseDto findByCommentPage(Long postId, Pageable pageable) {
        Page<C> commentPage = commentRepository.findByCommentPage(postId, pageable);
        List<CommentResponseDto> dtoList = convertPageToListDto(commentPage);

        return new CommentListResponseDto(dtoList, commentPage.getTotalPages());
    }

    // 하위 댓글 조회
    public CommentListResponseDto findByReplyPage(Long postId, Long parentId, Pageable pageable) {
        Page<C> replyPage = commentRepository.findByReplyPage(postId, parentId, pageable);
        List<CommentResponseDto> dtoList = convertPageToListDto(replyPage);

        return new CommentListResponseDto(dtoList, replyPage.getTotalPages());
    }

    private List<CommentResponseDto> convertPageToListDto(Page<C> page) {
        return page.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    // 수정
    @Transactional
    public Long update(CommentUpdateRequestDto requestDto, String email) {
        C comment = commentCommand.findById(requestDto.commentId());
        memberValidator(comment.getWriterId(), email);
        comment.update(requestDto);

        return comment.getId();
    }

    // 삭제
    @Transactional
    public Long delete(CommentDeleteRequestDto requestDto, String email) {
        C comment = commentCommand.findById(requestDto.commentId());
        memberValidator(comment.getWriterId(), email);
        commentRepository.delete(comment); // deleteById 는 내부적으로 findById가 존재해서 조회가 한 번 더 일어남

        return requestDto.commentId();
    }

    private void memberValidator(Long writerId, String email) {
        Member writer = memberCommand.findById(writerId);
        if (!writer.getEmail().equals(email)) {
            throw new CustomRuntimeException(ErrorCode.UNMATCHED_EMAIL);
        }
    }

}
