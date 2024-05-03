package org.example.entity.enums;

import lombok.Getter;

@Getter
public enum MessageStatusEnum {
    NO_READ(0, "未读"),
    READ(1, "已读");

    private Integer status;

    private String desc;

    MessageStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
