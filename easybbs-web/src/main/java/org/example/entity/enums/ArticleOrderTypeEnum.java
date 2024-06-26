package org.example.entity.enums;

import lombok.Getter;

@Getter
public enum ArticleOrderTypeEnum {

    HOT(0, "top_type desc,comment_count desc, good_count desc,read_count desc", "热榜"),
    SEND(1, "post_time asc", "发布"),
    NEW(2, "post_time desc", "最新");


    private Integer type;

    private String orderSql;

    private String desc;

    ArticleOrderTypeEnum(Integer type, String orderSql, String desc) {
        this.type = type;
        this.orderSql = orderSql;
        this.desc = desc;
    }

    public static ArticleOrderTypeEnum getByType(Integer type) {
        for (ArticleOrderTypeEnum value : ArticleOrderTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
