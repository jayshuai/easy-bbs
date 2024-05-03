package org.example.entity.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    DISABLED(0, "禁用"),
    ENABLE(1, "启用");
    final int status;
    final String desc;

    UserStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    UserStatusEnum getUserByStatus(int status) {
        for (UserStatusEnum value : UserStatusEnum.values()) {
            if (value.getStatus() == status) {
                return value;
            }
        }
        return null;
    }
}
