package org.example.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttachmentFileTypeEnum {
    ZIP(0,new String[]{".zip",".rar"},"压缩包");

    private Integer type;

    private String[] suffix;

    private String desc;

}
