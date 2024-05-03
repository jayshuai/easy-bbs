package org.example.dao;

import org.example.entity.ForumArticleAttachmentDownload;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户附件下载(ForumArticleAttachmentDownload)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-04 23:49:56
 */
public interface ForumArticleAttachmentDownloadDao {

    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    ForumArticleAttachmentDownload queryById(String fileId);


    /**
     * 统计总行数
     *
     * @param forumArticleAttachmentDownload 查询条件
     * @return 总行数
     */
    long count(ForumArticleAttachmentDownload forumArticleAttachmentDownload);

    /**
     * 新增数据
     *
     * @param forumArticleAttachmentDownload 实例对象
     * @return 影响行数
     */
    int insert(ForumArticleAttachmentDownload forumArticleAttachmentDownload);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ForumArticleAttachmentDownload> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ForumArticleAttachmentDownload> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ForumArticleAttachmentDownload> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ForumArticleAttachmentDownload> entities);

    int insertOrUpdate(@Param("entities") ForumArticleAttachmentDownload entities);

    /**
     * 修改数据
     *
     * @param forumArticleAttachmentDownload 实例对象
     * @return 影响行数
     */
    int update(ForumArticleAttachmentDownload forumArticleAttachmentDownload);

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 影响行数
     */
    int deleteById(String fileId);

    ForumArticleAttachmentDownload queryByIdAndUserId(String userId, String fileId);
}

