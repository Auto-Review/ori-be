ALTER TABLE code_post_bookmark
    ADD CONSTRAINT uq_codepost_member
        UNIQUE (code_post_id, member_id);

ALTER TABLE tilbookmark
    ADD CONSTRAINT uq_tilpost_email
        UNIQUE (tilpost_id, member_email);
