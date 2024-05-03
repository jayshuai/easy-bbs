package org.example.entity.vo;

import lombok.Data;

/**
 * 文件信息
 */
@Data
public class ForumArticleAttachmentVo {
    /**
     * 文件id
     */
    private String fileId;
    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 下载积分
     */
    private Integer integer;
}
