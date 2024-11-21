package org.example.autoreview.domain.notification.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class NotificationUpdateRequestDto {

    private Long id;
    private String content;
    private LocalDate reviewDay;
}
