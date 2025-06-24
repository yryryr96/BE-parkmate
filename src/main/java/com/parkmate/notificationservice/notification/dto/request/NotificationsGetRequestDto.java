package com.parkmate.notificationservice.notification.dto.request;

import com.parkmate.notificationservice.notification.vo.request.NotificationsGetRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationsGetRequestDto {

    private String receiverUuid;
    private String cursor;
    private Integer size;

    @Builder
    private NotificationsGetRequestDto(String receiverUuid, String cursor, Integer size) {
        this.receiverUuid = receiverUuid;
        this.cursor = cursor;
        this.size = size;
    }

    public static NotificationsGetRequestDto of(String receiverUuid, NotificationsGetRequestVo notificationsGetRequestVo) {
        return NotificationsGetRequestDto.builder()
                .receiverUuid(receiverUuid)
                .cursor(notificationsGetRequestVo.getCursor())
                .size(notificationsGetRequestVo.getSize())
                .build();
    }
}
