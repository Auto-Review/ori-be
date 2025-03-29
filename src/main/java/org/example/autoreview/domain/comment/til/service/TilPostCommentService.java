package org.example.autoreview.domain.comment.til.service;

import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.comment.base.CommentService;
import org.example.autoreview.domain.comment.base.dto.request.CommentSaveRequestDto;
import org.example.autoreview.domain.comment.til.entity.TilPostComment;
import org.example.autoreview.domain.comment.til.entity.TilPostCommentRepository;
import org.example.autoreview.domain.member.entity.Member;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.example.autoreview.domain.tilpost.entity.TILPost;
import org.example.autoreview.domain.tilpost.service.TilPostCommand;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TilPostCommentService extends CommentService<TilPostComment, TilPostCommentRepository> {

    private final TilPostCommand tilPostCommand;

    public TilPostCommentService(TilPostCommentRepository tilPostCommentRepository,
                                  TilPostCommentCommand tilPostCommentCommand,
                                  TilPostCommand tilPostCommand,
                                  MemberCommand memberCommand) {
        super(tilPostCommentRepository, tilPostCommentCommand, memberCommand);
        this.tilPostCommand = tilPostCommand;
    }

    @Override
    protected TilPostComment createReplyEntity(CommentSaveRequestDto requestDto, TilPostComment parent, Member writer) {
        TILPost tilPost = tilPostCommand.findById(requestDto.postId());
        return requestDto.toTilPostReplyEntity(tilPost, parent, writer);
    }

    @Override
    protected TilPostComment createCommentEntity(CommentSaveRequestDto requestDto, Member writer) {
        TILPost tilPost = tilPostCommand.findById(requestDto.postId());
        return requestDto.toTilPostCommentEntity(tilPost, writer);
    }

    @Override
    protected boolean isPostWriter(Long postId, String commentWriterEmail) {
        TILPost tilPost = tilPostCommand.findById(postId);
        Member member = memberCommand.findById(tilPost.getWriterId());
        return member.getEmail().equals(commentWriterEmail);
    }
}
