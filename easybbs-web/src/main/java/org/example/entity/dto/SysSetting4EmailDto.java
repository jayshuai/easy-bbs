package org.example.entity.dto;

import lombok.Data;

/**
 * 邮件设置
 */
@Data
public class SysSetting4EmailDto {

    //邮件标题

    private String emailTitle;
    //邮件内容

    public String emailContent;
}
