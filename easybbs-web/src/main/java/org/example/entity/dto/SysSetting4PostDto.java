package org.example.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysSetting4PostDto {
    /**
     * 发送积分
     */
    private Integer postIntegral;

    /**
     * 一天发帖量
     */
    private Integer postDayCountThreshold;
    /**
     * 每天上传图片数量
     */
    private Integer dayImageUploadCount;
    /**
     * 附件大小
     */
    private Integer attachmentSize;
}
