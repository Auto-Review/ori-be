package org.example.autoreview.global.scheduler.bookmark;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.autoreview.domain.bookmark.TILBookmark.service.TILBookmarkService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BookmarkScheduler {

    private final TILBookmarkService tilBookmarkService;

//    @Scheduled(cron = "0 10 6 * * ?") // 매달 10일 오전 6시
//    public void deleteUselessBookmark(){
//        log.info("start delete bookmark");
//        tilBookmarkService.deleteUseless();
//        log.info("end delete bookmark");
//    }
}
