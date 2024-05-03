package org.example.entity.enums;

import lombok.Getter;
import org.example.entity.constants.Constants;

@Getter
public enum FileUploadTypeEnum {
    ARTICLE_COVER("文章封面", new String[]{"png", "PNG", "jpg", "JPG", "jpeg", "JPEG", "gif", "GIF", "webp"}),
    ARTICLE_ATTACHMENT("文章附件", new String[]{".zip", ".ZIP", ".rar", ".RAR"}),

    COMMENT_IMAGE("评论图片", new String[]{"png", "PNG", "jpg", "JPG", "jpeg", "JPEG", "gif", "GIF", "webp"}),
    AVATAR("个人头像", new String[]{"png", "PNG", "jpg", "JPG", "jpeg", "JPEG", "gif", "GIF", "webp"});

    private String desc;

    private String[] suffixArray;

    FileUploadTypeEnum(String desc, String[] suffixArray) {
        this.suffixArray = suffixArray;
        this.desc = desc;
    }
}
