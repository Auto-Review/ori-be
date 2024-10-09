package org.example.autoreview.domain.member.sociallogin;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenPrefix {

    RANDOMCODE("RC"),
    REFRESHTOKEN("RT"),
    ;

    private final String prefix;
}
