package org.example.entity.enums;

import lombok.Getter;

@Getter
public enum DateTimePatterEnum {
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"), YYYY_MM_DD("yyyy-MM-dd"), YYYY_MM("yyyyMM");;

    private String patter;

    DateTimePatterEnum(String patter) {
        this.patter = patter;
    }
}
