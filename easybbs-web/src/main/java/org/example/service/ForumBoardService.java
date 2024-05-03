package org.example.service;

import org.example.entity.ForumBoard;

import java.util.List;

/**
 * 文章板块信息(ForumBoard)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:50:15
 */
public interface ForumBoardService {

    /**
     * 通过ID查询单条数据
     *
     * @param boardId 主键
     * @return 实例对象
     */
    ForumBoard queryById(Integer boardId);


    /**
     * 新增数据
     *
     * @param forumBoard 实例对象
     * @return 实例对象
     */
    ForumBoard insert(ForumBoard forumBoard);

    /**
     * 修改数据
     *
     * @param forumBoard 实例对象
     * @return 实例对象
     */
    ForumBoard update(ForumBoard forumBoard);

    /**
     * 通过主键删除数据
     *
     * @param boardId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer boardId);

    List<ForumBoard> getBoardTree(Integer postType);

}
