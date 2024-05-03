package org.example.entity;

import java.io.Serializable;

/**
 * 用户附件下载(ForumArticleAttachmentDownload)实体类
 *
 * @author makejava
 * @since 2024-04-04 23:49:56
 */
public class ForumArticleAttachmentDownload implements Serializable {
    private static final long serialVersionUID = 561884702578445491L;
/**
     * 文件ID
     */
    private String fileId;
/**
     * 用户id
     */
    private String userId;
/**
     * 文章ID
     */
    private String articleId;
/**
     * 文件下载次数
     */
    private Integer downloadCount;


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

}

