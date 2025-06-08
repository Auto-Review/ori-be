package org.example.autoreview.domain.comment.codepost.service;

import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.codepost.entity.CodePost;
import org.example.autoreview.domain.codepost.service.CodePostCommand;
import org.example.autoreview.domain.comment.base.CommentService;
import org.example.autoreview.domain.comment.base.dto.request.CommentSaveRequestDto;
import org.example.autoreview.domain.comment.codepost.entity.CodePostComment;
import org.example.autoreview.domain.comment.codepost.entity.CodePostCommentRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CodePostCommentService extends CommentService<CodePostComment, CodePostCommentRepository> {

    private final CodePostCommand codePostCommand;

    public CodePostCommentService(CodePostCommentCommand codePostCommentCommand,
                                  CodePostCommand codePostCommand,
                                  MemberCommand memberCommand) {
        super(codePostCommentCommand, memberCommand);
        this.codePostCommand = codePostCommand;
    }

    /**
     * code post 대댓글 Entity 생성 메서드이다.
     * 부모 클래스에서는 CodePost Entity를 사용할 수 없어서 자식 클래스에 생성
     */
    @Override
    protected CodePostComment createReplyEntity(CommentSaveRequestDto requestDto, CodePostComment parent, Member writer) {
        CodePost codePost = codePostCommand.findById(requestDto.postId());
        return requestDto.toCodePostReplyEntity(codePost, parent, writer);
    }

    /**
     * code post 댓글 Entity 생성 메서드이다.
     * 부모 클래스에서는 CodePost Entity를 사용할 수 없어서 자식 클래스에 생성
     */
    @Override
    protected CodePostComment createCommentEntity(CommentSaveRequestDto requestDto, Member writer) {
        CodePost codePost = codePostCommand.findById(requestDto.postId());
        return requestDto.toCodePostCommentEntity(codePost, writer);
    }

    /**
     * code post 대댓글 Entity 생성 메서드이다.
     *
     */
    @Override
    protected boolean isPostWriter(Long postId, String commentWriterEmail) {
        CodePost codePost = codePostCommand.findById(postId);
        Member member = memberCommand.findById(codePost.getWriterId());
        return member.getEmail().equals(commentWriterEmail);
    }
}
