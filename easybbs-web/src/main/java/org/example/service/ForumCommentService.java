package org.example.service;

import org.example.entity.ForumComment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 评论(ForumComment)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:50:33
 */
public interface ForumCommentService {

    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    ForumComment queryById(Integer commentId);


    /**
     * 新增数据
     *
     * @param forumComment 实例对象
     * @return 实例对象
     */
    ForumComment insert(ForumComment forumComment);

    /**
     * 修改数据
     *
     * @param forumComment 实例对象
     * @return 实例对象
     */
    ForumComment update(ForumComment forumComment);

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer commentId);

    List<ForumComment> findCountByParam(Map<String, Object> commentQuery);

    List<ForumComment> findListByParam(Map<String, String> query);

    void postComment(ForumComment forumComment, MultipartFile image);
}
