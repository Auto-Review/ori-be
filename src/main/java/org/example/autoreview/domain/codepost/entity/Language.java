package org.example.autoreview.domain.codepost.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Language {

    ALL("all","txt"),
    KOTLIN("kotlin","kt"),
    JAVASCRIPT("javascript", "js"),
    PYTHON("python", "py"),
    JAVA("java", "java"),
    CSHARP("csharp", "cs"),
    CPP("cpp", "cpp"),
    C("c", "c"),
    RUBY("ruby", "rb"),
    GO("go", "go");

    private final String type;
    private final String fileExtension;

    Language(String type, String fileExtension) {
        this.type = type;
        this.fileExtension = fileExtension;
    }

    public static Optional<Language> of(String name) {
        return Arrays.stream(values())
                .filter(lang -> lang.type.equalsIgnoreCase(name))
                .findFirst();
    }
}
