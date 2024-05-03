package org.example.entity.enums;

import lombok.Getter;

@Getter
public enum UpdateArticleCountTypeEnum {
    READ_COUNT(0, "阅读数"),
    GOOD_COUNT(1, "点赞数"),
    COMMENT_COUNT(2, "评论数");

    public static UpdateArticleCountTypeEnum getByType(Integer type){
        for (UpdateArticleCountTypeEnum value : UpdateArticleCountTypeEnum.values()) {
            if (value.getType()==type){
                return value;
            }
        }
        return null;
    }

    private Integer type;

    private String desc;

    UpdateArticleCountTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
