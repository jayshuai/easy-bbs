package org.example.service;

import org.apache.ibatis.annotations.Param;
import org.example.entity.ForumArticle;
import org.example.entity.ForumArticleAttachment;
import org.example.entity.dto.ForumArticleDto;
import org.example.entity.vo.PaginationResultVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文章信息(ForumArticle)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:37:38
 */
public interface ForumArticleService {

    /**
     * 通过ID查询单条数据
     *
     * @param articleId 主键
     * @return 实例对象
     */
    ForumArticle queryById(String articleId);


    /**
     * 新增数据
     *
     * @param forumArticle 实例对象
     * @return 实例对象
     */
    ForumArticle insert(ForumArticle forumArticle);

    /**
     * 修改数据
     *
     * @param forumArticle 实例对象
     * @return 实例对象
     */
    ForumArticle update(ForumArticle forumArticle);

    /**
     * 通过主键删除数据
     *
     * @param articleId 主键
     * @return 是否成功
     */
    boolean deleteById(String articleId);

    PaginationResultVO findListByPage(ForumArticleDto forumArticleDto);


    ForumArticle readArticle(String articleId);

    void postArticle(Boolean isAdmin, ForumArticle forumArticle, ForumArticleAttachment forumArticleAttachment, MultipartFile cover, MultipartFile attachment);

    void updateArticle(Boolean isAdmin, ForumArticle forumArticle, ForumArticleAttachment forumArticleAttachment, MultipartFile cover, MultipartFile attachment);
}
