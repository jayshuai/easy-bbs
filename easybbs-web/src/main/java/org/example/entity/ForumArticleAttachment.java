package org.example.entity;

import java.io.Serializable;

/**
 * 文件信息(ForumArticleAttachment)实体类
 *
 * @author makejava
 * @since 2024-04-04 23:46:13
 */
public class ForumArticleAttachment implements Serializable {
    private static final long serialVersionUID = 116141206891499931L;
/**
     * 文件ID
     */
    private String fileId;
/**
     * 文章ID
     */
    private String articleId;
/**
     * 用户id
     */
    private String userId;
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
     * 文件路径
     */
    private String filePath;
/**
     * 文件类型
     */
    private Integer fileType;
/**
     * 下载所需积分
     */
    private Integer integral;


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

}

