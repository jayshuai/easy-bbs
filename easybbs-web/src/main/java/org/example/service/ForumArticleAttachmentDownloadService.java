package org.example.service;

import org.example.entity.ForumArticleAttachmentDownload;

/**
 * 用户附件下载(ForumArticleAttachmentDownload)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:49:56
 */
public interface ForumArticleAttachmentDownloadService {

    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    ForumArticleAttachmentDownload queryById(String fileId);


    /**
     * 新增数据
     *
     * @param forumArticleAttachmentDownload 实例对象
     * @return 实例对象
     */
    ForumArticleAttachmentDownload insert(ForumArticleAttachmentDownload forumArticleAttachmentDownload);

    /**
     * 修改数据
     *
     * @param forumArticleAttachmentDownload 实例对象
     * @return 实例对象
     */
    ForumArticleAttachmentDownload update(ForumArticleAttachmentDownload forumArticleAttachmentDownload);

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 是否成功
     */
    boolean deleteById(String fileId);

    ForumArticleAttachmentDownload getFileDownLoadByUserIdAndFileId(String userId, String fileId);
}
