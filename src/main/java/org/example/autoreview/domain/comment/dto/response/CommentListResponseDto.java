package org.example.autoreview.domain.comment.dto.response;

import java.util.List;

public record CommentListResponseDto(

        List<CommentResponseDto> commentList,
        int totalPage
) {

}
