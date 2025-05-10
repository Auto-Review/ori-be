create table code_post_bookmark (
    id bigint not null auto_increment,
    code_post_id bigint not null,
    is_deleted bit not null default false,
    member_id bigint,
    create_date datetime(6) not null,
    update_date datetime(6) not null,
    primary key (id),
    constraint fk_code_post_bookmark_member
        foreign key (member_id) references member (id)
);
