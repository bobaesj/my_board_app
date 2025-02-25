package com.bit.myboardapp.controller;

import com.bit.myboardapp.dto.NotificationDto;
import com.bit.myboardapp.dto.ResponseDto;
import com.bit.myboardapp.entity.Notification;
import com.bit.myboardapp.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    // 알람 조회
    @GetMapping
    public ResponseEntity<?> getNotifications(HttpServletRequest request) {
        ResponseDto<List<NotificationDto>> responseDto = new ResponseDto<>();
        String email = (String) request.getAttribute("userEmail");

        log.info("getNotifications email: {}", email);
        List<NotificationDto> notificationList = notificationService.getNotifications(email);
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatusMessage("ok");
        responseDto.setItem(notificationList);

        return ResponseEntity.ok(responseDto);
    }

    // 알람 단건 조회 및 읽음 처리
    @GetMapping("/{noticeId}")
    public ResponseEntity<?> getNotificationById(@PathVariable Long noticeId) {
        ResponseDto<NotificationDto> responseDto = new ResponseDto<>();

        log.info("getNotificationById noticeId: {}", noticeId);
        NotificationDto notificationDto = notificationService.getNotification(noticeId);
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatusMessage("ok");
        responseDto.setItem(notificationDto);

        return ResponseEntity.ok(responseDto);
    }


}
