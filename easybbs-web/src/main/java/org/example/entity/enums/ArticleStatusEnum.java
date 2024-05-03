package org.example.entity.enums;

import lombok.Getter;

@Getter
public enum ArticleStatusEnum {
    DEL(-1, "已删除"),
    NO_AUDIT(0, "待审核"),
    AUDIT(1, "已审核");

    private Integer status;

    private String desc;

    ArticleStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static ArticleStatusEnum getByStatus(Integer type) {
        for (ArticleStatusEnum value : ArticleStatusEnum.values()) {
            if (value.getStatus().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
