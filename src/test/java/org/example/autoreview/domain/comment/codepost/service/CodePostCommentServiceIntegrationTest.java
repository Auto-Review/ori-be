package org.example.autoreview.domain.comment.codepost.service;

import org.example.autoreview.domain.codepost.service.CodePostCommand;
import org.example.autoreview.domain.member.service.MemberCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CodePostCommentServiceIntegrationTest {

    @Autowired
    private CodePostCommentCommand codePostCommentCommand;

    @Autowired
    private CodePostCommand codePostCommand;

    @Autowired
    private MemberCommand memberCommand;

    @Autowired
    private CodePostCommentService codePostCommentService;


}
