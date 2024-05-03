package org.example.service;

import org.example.entity.ForumArticleAttachment;
import org.example.entity.dto.SessionWebUserDto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文件信息(ForumArticleAttachment)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:46:13
 */
public interface ForumArticleAttachmentService {

    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    ForumArticleAttachment queryById(String fileId);


    /**
     * 新增数据
     *
     * @param forumArticleAttachment 实例对象
     * @return 实例对象
     */
    ForumArticleAttachment insert(ForumArticleAttachment forumArticleAttachment);

    /**
     * 修改数据
     *
     * @param forumArticleAttachment 实例对象
     * @return 实例对象
     */
    ForumArticleAttachment update(ForumArticleAttachment forumArticleAttachment);

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 是否成功
     */
    boolean deleteById(String fileId);

    List<ForumArticleAttachment> getAttachmentByArticleId(String articleId);

    ForumArticleAttachment downAttachment(String fileId, SessionWebUserDto userInfoFromSession);
}
