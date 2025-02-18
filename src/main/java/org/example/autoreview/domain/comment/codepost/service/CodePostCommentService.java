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

    public CodePostCommentService(CodePostCommentRepository codePostCommentRepository,
                                  CodePostCommentCommand codePostCommentCommand,
                                  CodePostCommand codePostCommand,
                                  MemberCommand memberCommand) {
        super(codePostCommentRepository, codePostCommentCommand, memberCommand);
        this.codePostCommand = codePostCommand;
    }

    @Override
    protected CodePostComment createReplyEntity(CommentSaveRequestDto requestDto, CodePostComment parent, Member writer) {
        CodePost codePost = codePostCommand.findById(requestDto.postId());
        return requestDto.toReplyEntity(codePost, parent, writer);
    }

    @Override
    protected CodePostComment createCommentEntity(CommentSaveRequestDto requestDto, Member writer) {
        CodePost codePost = codePostCommand.findById(requestDto.postId());
        return requestDto.toCommentEntity(codePost, writer);
    }
}
