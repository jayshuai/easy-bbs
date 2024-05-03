package org.example.dao;

import org.apache.ibatis.annotations.Param;
import org.example.entity.ForumArticleAttachment;

import java.util.List;

/**
 * 文件信息(ForumArticleAttachment)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-04 23:46:12
 */
public interface ForumArticleAttachmentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    ForumArticleAttachment queryById(String fileId);


    /**
     * 统计总行数
     *
     * @param forumArticleAttachment 查询条件
     * @return 总行数
     */
    long count(ForumArticleAttachment forumArticleAttachment);

    /**
     * 新增数据
     *
     * @param forumArticleAttachment 实例对象
     * @return 影响行数
     */
    int insert(ForumArticleAttachment forumArticleAttachment);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ForumArticleAttachment> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ForumArticleAttachment> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ForumArticleAttachment> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ForumArticleAttachment> entities);

    /**
     * 修改数据
     *
     * @param forumArticleAttachment 实例对象
     * @return 影响行数
     */
    int update(ForumArticleAttachment forumArticleAttachment);

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 影响行数
     */
    int deleteById(String fileId);

    List<ForumArticleAttachment> queryByArticleId(String articleId);

    void updateDownloadCount(String fileId);
}

