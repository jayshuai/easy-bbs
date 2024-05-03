package org.example.dao;

import org.apache.ibatis.annotations.Param;
import org.example.entity.ForumArticle;

import java.util.List;


/**
 * 文章信息(ForumArticle)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-04 23:37:36
 */
public interface ForumArticleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param articleId 主键
     * @return 实例对象
     */
    ForumArticle queryById(String articleId);


    /**
     * 统计总行数
     *
     * @param forumArticle 查询条件
     * @return 总行数
     */
    long count(ForumArticle forumArticle);

    /**
     * 新增数据
     *
     * @param forumArticle 实例对象
     * @return 影响行数
     */
    int insert(ForumArticle forumArticle);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ForumArticle> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ForumArticle> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ForumArticle> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ForumArticle> entities);

    /**
     * 修改数据
     *
     * @param forumArticle 实例对象
     * @return 影响行数
     */
    int update(ForumArticle forumArticle);

    /**
     * 通过主键删除数据
     *
     * @param articleId 主键
     * @return 影响行数
     */
    int deleteById(String articleId);

    List<ForumArticle> queryPageByConditon(@Param("queryArticle") ForumArticle queryArticle, @Param("sqlString") String sqlString, @Param("pageNo") Integer pageNo, @Param("pageSize") int size);

    void updateArticleCount(@Param("updateType") Integer updateType, @Param("changeCount") Integer changeCount,@Param("articleId") String articleId);
}

