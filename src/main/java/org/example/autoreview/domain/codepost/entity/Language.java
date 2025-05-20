package org.example.autoreview.domain.codepost.entity;

import lombok.Getter;

@Getter
public enum Language {

    ALL("all"),
    JAVASCRIPT("javascript"),
    PYTHON("python"),
    JAVA("java"),
    CSHARP("csharp"),
    CPP("cpp"),
    C("c"),
    RUBY("ruby"),
    GO("go");

    private final String type;

    Language(String type) {
        this.type = type;
    }

    public static Language of(String type) {
        for (Language lt : Language.values()) {
            if (lt.type.equals(type)) {
                return lt;
            }
        }
        return null;
    }
}
