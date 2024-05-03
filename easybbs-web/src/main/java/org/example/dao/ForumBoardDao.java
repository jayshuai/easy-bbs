package org.example.dao;

import org.example.entity.ForumBoard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章板块信息(ForumBoard)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-04 23:50:15
 */
public interface ForumBoardDao {

    /**
     * 通过ID查询单条数据
     *
     * @param boardId 主键
     * @return 实例对象
     */
    ForumBoard queryById(Integer boardId);


    /**
     * 统计总行数
     *
     * @param forumBoard 查询条件
     * @return 总行数
     */
    long count(ForumBoard forumBoard);

    /**
     * 新增数据
     *
     * @param forumBoard 实例对象
     * @return 影响行数
     */
    int insert(ForumBoard forumBoard);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ForumBoard> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ForumBoard> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ForumBoard> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ForumBoard> entities);

    /**
     * 修改数据
     *
     * @param forumBoard 实例对象
     * @return 影响行数
     */
    int update(ForumBoard forumBoard);

    /**
     * 通过主键删除数据
     *
     * @param boardId 主键
     * @return 影响行数
     */
    int deleteById(Integer boardId);

    List<ForumBoard> queryAllBoard(@Param("postType") Integer postType);

}

