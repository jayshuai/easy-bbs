package org.example.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import org.example.dao.ForumBoardDao;
import org.example.entity.ForumBoard;
import org.example.service.ForumBoardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文章板块信息(ForumBoard)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:50:15
 */
@Service("forumBoardService")
public class ForumBoardServiceImpl implements ForumBoardService {
    @Resource
    private ForumBoardDao forumBoardDao;

    /**
     * 通过ID查询单条数据
     *
     * @param boardId 主键
     * @return 实例对象
     */
    @Override
    public ForumBoard queryById(Integer boardId) {
        return this.forumBoardDao.queryById(boardId);
    }


    /**
     * 新增数据
     *
     * @param forumBoard 实例对象
     * @return 实例对象
     */
    @Override
    public ForumBoard insert(ForumBoard forumBoard) {
        this.forumBoardDao.insert(forumBoard);
        return forumBoard;
    }

    /**
     * 修改数据
     *
     * @param forumBoard 实例对象
     * @return 实例对象
     */
    @Override
    public ForumBoard update(ForumBoard forumBoard) {
        this.forumBoardDao.update(forumBoard);
        return this.queryById(forumBoard.getBoardId());
    }

    /**
     * 通过主键删除数据
     *
     * @param boardId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer boardId) {
        return this.forumBoardDao.deleteById(boardId) > 0;
    }

    @Override
    public List<ForumBoard> getBoardTree(Integer postType) {
        List<ForumBoard> forumBoards = forumBoardDao.queryAllBoard(postType);
        return convertLine2Tree(forumBoards, 0);
    }

    private List<ForumBoard> convertLine2Tree(List<ForumBoard> forumBoards, Integer pid) {
        if (CollectionUtil.isEmpty(forumBoards)) {
            return new ArrayList<>();
        }
        List<ForumBoard> children = new ArrayList<>();
        for (ForumBoard board : forumBoards) {
            if (board.getPBoardId().equals(pid)) {
                board.setChildren(convertLine2Tree(forumBoards, board.getBoardId()));
                children.add(board);
            }
        }
        return children;
    }
}