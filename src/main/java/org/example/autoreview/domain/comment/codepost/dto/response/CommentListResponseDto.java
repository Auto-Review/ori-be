package org.example.autoreview.domain.comment.codepost.dto.response;

import java.util.List;

public record CommentListResponseDto(

        List<CommentResponseDto> commentList,
        int totalPage
) {

}
