package org.example.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ComentTopTypeEnum {
    NO_TOP(0,"未置顶"),
    TOP(1,"已置顶");


    private Integer type;

    private String desc;
}
