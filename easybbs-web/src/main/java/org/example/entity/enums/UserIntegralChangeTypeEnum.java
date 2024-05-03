package org.example.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserIntegralChangeTypeEnum {
    ADD(1, "增加"),
    REDUCE(-1, "减少");

    private Integer changeType;

    private String desc;


}
