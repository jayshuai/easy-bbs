package org.example.service.impl;

import org.example.dao.ForumArticleAttachmentDownloadDao;
import org.example.entity.ForumArticleAttachmentDownload;
import org.example.service.ForumArticleAttachmentDownloadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户附件下载(ForumArticleAttachmentDownload)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:49:56
 */
@Service("forumArticleAttachmentDownloadService")
public class ForumArticleAttachmentDownloadServiceImpl implements ForumArticleAttachmentDownloadService {
    @Resource
    private ForumArticleAttachmentDownloadDao forumArticleAttachmentDownloadDao;

    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    @Override
    public ForumArticleAttachmentDownload queryById(String fileId) {
        return this.forumArticleAttachmentDownloadDao.queryById(fileId);
    }


    /**
     * 新增数据
     *
     * @param forumArticleAttachmentDownload 实例对象
     * @return 实例对象
     */
    @Override
    public ForumArticleAttachmentDownload insert(ForumArticleAttachmentDownload forumArticleAttachmentDownload) {
        this.forumArticleAttachmentDownloadDao.insert(forumArticleAttachmentDownload);
        return forumArticleAttachmentDownload;
    }

    /**
     * 修改数据
     *
     * @param forumArticleAttachmentDownload 实例对象
     * @return 实例对象
     */
    @Override
    public ForumArticleAttachmentDownload update(ForumArticleAttachmentDownload forumArticleAttachmentDownload) {
        this.forumArticleAttachmentDownloadDao.update(forumArticleAttachmentDownload);
        return this.queryById(forumArticleAttachmentDownload.getFileId());
    }

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String fileId) {
        return this.forumArticleAttachmentDownloadDao.deleteById(fileId) > 0;
    }

    @Override
    public ForumArticleAttachmentDownload getFileDownLoadByUserIdAndFileId(String userId, String fileId) {
        return this.forumArticleAttachmentDownloadDao.queryByIdAndUserId(userId, fileId);
    }
}
