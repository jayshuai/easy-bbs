package org.example.entity.dto;

import lombok.Data;

/**
 * 评论设置
 */
@Data
public class SysSetting4CommentDto {
    /**
     * 评论积分
     */
    private Integer commentIntegral;
    /**
     * 评论阈值
     */
    private Integer commentDayCountThreshold;
    /**
     * 评论是否打开
     */
    private Boolean commentOpen;
}
