package org.example.autoreview.domain.comment.codepost.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.service.CodePostCommand;
import org.example.autoreview.domain.comment.codepost.dto.request.CommentDeleteRequestDto;
import org.example.autoreview.domain.comment.codepost.dto.request.CommentSaveRequestDto;
import org.example.autoreview.domain.comment.codepost.dto.request.CommentUpdateRequestDto;
import org.example.autoreview.domain.comment.codepost.dto.response.CommentListResponseDto;
import org.example.autoreview.domain.comment.codepost.dto.response.CommentResponseDto;
import org.example.autoreview.domain.comment.codepost.entity.CodePostComment;
import org.example.autoreview.domain.comment.codepost.entity.CommentRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.example.autoreview.global.exception.base_exceptions.CustomRuntimeException;
import org.example.autoreview.global.exception.errorcode.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentCommand commentCommand;
    private final CodePostCommand codePostCommand;
    private final MemberCommand memberCommand;

    @Transactional
    public Long save(CommentSaveRequestDto requestDto, String email) {
        CodePost codePost = codePostCommand.findById(requestDto.codePostId());
        Member writer = memberCommand.findByEmail(email);

        if (requestDto.parentId() != null) {
            CodePostComment parent = commentCommand.findById(requestDto.parentId());
            return commentRepository.save(requestDto.toReplyEntity(codePost, parent, writer)).getId();
        }
        return commentRepository.save(requestDto.toCommentEntity(codePost, writer)).getId();
    }

    // 상위 댓글 조회
    public CommentListResponseDto findByCommentPage(Long codePostId, Pageable pageable) {
        Page<CodePostComment> commentPage = commentRepository.findByCommentPage(codePostId, pageable);
        List<CommentResponseDto> dtoList = convertPageToListDto(commentPage);

        return new CommentListResponseDto(dtoList, commentPage.getTotalPages());
    }

    // 하위 댓글 조회
    public CommentListResponseDto findByReplyPage(Long codePostId, Long parentId, Pageable pageable) {
        Page<CodePostComment> replyPage = commentRepository.findByReplyPage(codePostId, parentId, pageable);
        List<CommentResponseDto> dtoList = convertPageToListDto(replyPage);

        return new CommentListResponseDto(dtoList, replyPage.getTotalPages());
    }

    private List<CommentResponseDto> convertPageToListDto(Page<CodePostComment> page) {
        return page.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    // 수정
    @Transactional
    public Long update(CommentUpdateRequestDto requestDto, String email) {
        CodePostComment codePostComment = commentCommand.findById(requestDto.commentId());
        memberValidator(codePostComment.getWriterId(), email);
        codePostComment.update(requestDto);

        return codePostComment.getId();
    }

    // 삭제
    @Transactional
    public Long delete(CommentDeleteRequestDto requestDto, String email) {
        CodePostComment codePostComment = commentCommand.findById(requestDto.commentId());
        memberValidator(codePostComment.getWriterId(), email);
        commentRepository.delete(codePostComment); // deleteById 는 내부적으로 findById가 존재해서 조회가 한 번 더 일어남

        return requestDto.commentId();
    }

    private void memberValidator(Long writerId, String email) {
        Member writer = memberCommand.findById(writerId);
        if (!writer.getEmail().equals(email)) {
            throw new CustomRuntimeException(ErrorCode.UNMATCHED_EMAIL);
        }
    }
}
