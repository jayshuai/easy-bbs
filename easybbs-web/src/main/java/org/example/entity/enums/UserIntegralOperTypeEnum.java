package org.example.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserIntegralOperTypeEnum {
    REGISTER(1, "账号注册"),
    USER_DOWNLOAD_ATTACHMENT(2, "下载附件"),
    DOWNLOAD_ATTACHMENT(3, "附件下载"),
    POST_COMMENT(4, "发布评论"),
    ADMIN(6, "管理员操作"),
    DEL_ARTICLE(7, "文章被删除"),
    DEL_COMMENT(8, "评论被删除");

    private Integer operType;

    private String desc;

    public static UserIntegralOperTypeEnum getByType(String opertType) {
        for (UserIntegralOperTypeEnum value : UserIntegralOperTypeEnum.values()) {
            if (value.getOperType().equals(opertType)) {
                return value;
            }
        }
        return null;
    }
}
