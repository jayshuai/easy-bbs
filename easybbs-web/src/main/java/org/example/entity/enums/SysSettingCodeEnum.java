package org.example.entity.enums;

import lombok.Getter;

@Getter
public enum SysSettingCodeEnum {
    AUDIT("audit", "org.example.entity.dto.SysSetting4AuditDot", "sysSetting4AuditDot", "审核设置"),
    COMMENT("comment", "org.example.entity.dto.SysSetting4CommentDto", "sysSetting4CommentDto", "评论设置"),
    POST("post", "org.example.entity.dto.SysSetting4PostDto", "sysSetting4PostDto", "发帖设置"),
    LIKE("like", "org.example.entity.dto.SysSetting4LikeDto", "sysSetting4LikeDto", "点赞设置"),
    REGISTER("register", "org.example.entity.dto.SysSetting4RegisterDto", "sysSetting4RegisterDto", "注册设置"),
    EMAIL("email", "org.example.entity.dto.SysSetting4EmailDto", "sysSetting4EmailDto", "邮件设置");

    private String code;

    private String clazz;

    private String proName;

    private String desc;

    SysSettingCodeEnum(String code, String clazz, String proName, String desc) {
        this.code = code;
        this.clazz = clazz;
        this.proName = proName;
        this.desc = desc;
    }

    public static SysSettingCodeEnum getEnumByCode(String code) {
        for (SysSettingCodeEnum value : SysSettingCodeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
