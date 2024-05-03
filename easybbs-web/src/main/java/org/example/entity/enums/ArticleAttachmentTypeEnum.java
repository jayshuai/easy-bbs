package org.example.entity.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleAttachmentTypeEnum {
    NO(0, "没有附件"),
    YES(1, "有附件");

    private Integer type;

    private String desc;

    public static ArticleAttachmentTypeEnum getByCode(Integer code) {
        for (ArticleAttachmentTypeEnum value : ArticleAttachmentTypeEnum.values()) {
            if (ObjectUtil.equal(value.getType(), code)) {
                return value;
            }
        }
        return null;
    }
}
