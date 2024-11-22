package org.example.autoreview.domain.notification.dto.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationUpdateRequestDto {

    private Long id;
    private String content;
    private LocalDate reviewDay;
}
