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

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class CommentService<C extends Comment, R extends CommentRepository<C>> {
    private final String SECRETE_COMMENT = "비밀 댓글 입니다.";

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

    // User 상위 댓글 조회
    public CommentListResponseDto userFindCommentPage(Long postId, Pageable pageable, String email) {
        Page<C> commentPage = commentRepository.findByCommentPage(postId, pageable);
        List<CommentResponseDto> dtoList = new ArrayList<>();

        for (C c : commentPage.getContent()) {
            Member writer = memberCommand.findById(c.getWriterId());
            // 공개 or 게시글 작성자 or 댓글 작성자 일 경우
            if (c.isPublic() || isPostWriter(postId,email) || writer.getEmail().equals(email)) {
                dtoList.add(new CommentResponseDto(c,c.getBody(),writer.getEmail(),writer.getNickname()));
                continue;
            }
            dtoList.add(new CommentResponseDto(c,SECRETE_COMMENT,writer.getEmail(),writer.getNickname()));
        }

        return new CommentListResponseDto(dtoList, commentPage.getTotalPages());
    }

    protected abstract boolean isPostWriter(Long postId, String email);

    // User 하위 댓글 조회
    public CommentListResponseDto userFindReplyPage(Long postId, Long parentId, Pageable pageable, String email) {
        Page<C> replyPage = commentRepository.findByReplyPage(postId, parentId, pageable);
        List<CommentResponseDto> dtoList = new ArrayList<>();

        for (C c : replyPage.getContent()) {
            Member writer = memberCommand.findById(c.getWriterId());
            // 공개 or 언급된 사용자 or 댓글 작성자 일 경우
            if (c.isPublic() || c.getMentionEmail().equals(email) || writer.getEmail().equals(email)) {
                dtoList.add(new CommentResponseDto(c,c.getBody(),writer.getEmail(),writer.getNickname()));
                continue;
            }
            dtoList.add(new CommentResponseDto(c,SECRETE_COMMENT,writer.getEmail(),writer.getNickname()));
        }
        return new CommentListResponseDto(dtoList, replyPage.getTotalPages());
    }

    // Guest 상위 댓글 조회
    public CommentListResponseDto guestFindCommentPage(Long postId, Pageable pageable) {
        Page<C> commentPage = commentRepository.findByCommentPage(postId, pageable);
        List<CommentResponseDto> dtoList = new ArrayList<>();

        return getCommentListResponseDto(commentPage, dtoList);
    }

    // Guest 하위 댓글 조회
    public CommentListResponseDto guestFindReplyPage(Long postId, Long parentId, Pageable pageable) {
        Page<C> replyPage = commentRepository.findByReplyPage(postId, parentId, pageable);
        List<CommentResponseDto> dtoList = new ArrayList<>();

        return getCommentListResponseDto(replyPage, dtoList);
    }

    private CommentListResponseDto getCommentListResponseDto(Page<C> replyPage, List<CommentResponseDto> dtoList) {
        for (C c : replyPage.getContent()) {
            Member writer = memberCommand.findById(c.getWriterId());
            if (c.isPublic()) {
                dtoList.add(new CommentResponseDto(c,c.getBody(),writer.getEmail(),writer.getNickname()));
                continue;
            }
            dtoList.add(new CommentResponseDto(c,SECRETE_COMMENT,writer.getEmail(),writer.getNickname()));
        }
        return new CommentListResponseDto(dtoList, replyPage.getTotalPages());
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
