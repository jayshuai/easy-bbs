package org.example.entity.enums;

import lombok.Getter;

@Getter
public enum EditorTypeEnum {
    RICH(0, "富文本 "),
    MARKDOWN(1, "Markdown");


    private Integer type;

    private String desc;

    EditorTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static EditorTypeEnum getByType(Integer type) {
        for (EditorTypeEnum value : EditorTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
