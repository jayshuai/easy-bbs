package org.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.builder.BuilderException;
import org.example.dao.ForumArticleDao;
import org.example.dao.ForumCommentDao;
import org.example.dao.UserInfoDao;
import org.example.entity.ForumArticle;
import org.example.entity.ForumComment;
import org.example.entity.UserInfo;
import org.example.entity.UserMessage;
import org.example.entity.constants.Constants;
import org.example.entity.dto.FileUploadDto;
import org.example.entity.enums.*;
import org.example.exception.BusinessException;
import org.example.service.ForumCommentService;
import org.example.service.UserInfoService;
import org.example.service.UserMessageService;
import org.example.utils.FileUtils;
import org.example.utils.SysCacheUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 评论(ForumComment)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:50:33
 */
@Service("forumCommentService")
public class ForumCommentServiceImpl implements ForumCommentService {
    @Resource
    private ForumCommentDao forumCommentDao;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private ForumArticleDao forumArticleDao;
    @Resource
    private UserMessageService userMessageService;
    @Resource
    private FileUtils fileUtils;

    /**
     * 通过ID查询单条数据
     *
     * @param commentId 主键
     * @return 实例对象
     */
    @Override
    public ForumComment queryById(Integer commentId) {
        return this.forumCommentDao.queryById(commentId);
    }


    /**
     * 新增数据
     *
     * @param forumComment 实例对象
     * @return 实例对象
     */
    @Override
    public ForumComment insert(ForumComment forumComment) {
        this.forumCommentDao.insert(forumComment);
        return forumComment;
    }

    /**
     * 修改数据
     *
     * @param forumComment 实例对象
     * @return 实例对象
     */
    @Override
    public ForumComment update(ForumComment forumComment) {
        this.forumCommentDao.update(forumComment);
        return this.queryById(forumComment.getCommentId());
    }

    /**
     * 通过主键删除数据
     *
     * @param commentId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer commentId) {
        return this.forumCommentDao.deleteById(commentId) > 0;
    }

    @Override
    public List<ForumComment> findCountByParam(Map<String, Object> commentQuery) {
        return null;
    }

    @Override
    public List<ForumComment> findListByParam(Map<String, String> query) {
        return this.forumCommentDao.forumComment(query);
    }

    @Override
    public void postComment(ForumComment forumComment, MultipartFile image) {
        ForumArticle forumArticle = forumArticleDao.queryById(forumComment.getArticleId());
        if (forumArticle == null || !ArticleStatusEnum.AUDIT.getStatus().equals(forumArticle.getStatus())) {
            throw new BusinessException("评论文章不存在");
        }
        ForumComment pComment = null;
        if (forumComment.getPCommentId() != 0) {
            pComment = forumCommentDao.queryById(forumComment.getCommentId());
            if (pComment == null) {
                throw new BusinessException("回复的评论不存在");
            }
        }
        //判断回复的用户
        if (!StrUtil.isEmpty(forumComment.getReplyUserId())) {
            UserInfo userInfo = userInfoService.queryById(forumComment.getReplyUserId());
            if (ObjectUtil.isEmpty(userInfo)) {
                throw new BuilderException("回复的用户不存在");
            }
            forumComment.setReplyNickName(userInfo.getNickName());
        }
        forumComment.setPostTime(new Date());
        if (image != null) {
            FileUploadDto fileUploadDto = fileUtils.uploadFile2Local(image, Constants.FILE_FOLDER_IMAGE, FileUploadTypeEnum.COMMENT_IMAGE);
            forumComment.setImgPath(fileUploadDto.getLocalPath());
        }
        Boolean needAudit = SysCacheUtils.getSysSetting().getSysSetting4AuditDot().getCommentAudit();
        forumComment.setStatus(needAudit ? 0 : 1);
        forumCommentDao.insert(forumComment);
        if (needAudit) {
            return;
        }
        updateCommentInfo(forumComment, forumArticle, pComment);
    }

    public void updateCommentInfo(ForumComment comment, ForumArticle article, ForumComment pComment) {
        Integer commentIntegral = SysCacheUtils.getSysSetting().getSysSetting4CommentDto().getCommentIntegral();
        if (commentIntegral > 0) {
            userInfoService.updateUserIntegral(comment.getUserId(), UserIntegralOperTypeEnum.POST_COMMENT,
                    UserIntegralChangeTypeEnum.ADD.getChangeType(), commentIntegral);
        }
        if (comment.getPCommentId() == 0) {
            forumArticleDao.updateArticleCount(UpdateArticleCountTypeEnum.COMMENT_COUNT.getType(), Constants.ONE, comment.getArticleId());
        }
        //记录消息
        UserMessage userMessage = new UserMessage();
        userMessage.setMessageType(MessageTypeEnum.COMMENT.getType());
        userMessage.setArticleTitle(comment.getArticleId());
        userMessage.setCommentId(comment.getCommentId());
        userMessage.setSendNickName(comment.getNickName());
        userMessage.setSendUserId(comment.getUserId());
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        userMessage.setArticleTitle(article.getTitle());
        if (comment.getPCommentId() == 0) {
            userMessage.setReceivedUserId(article.getUserId());
        } else if (comment.getPCommentId() != 0 && StrUtil.isEmpty(comment.getReplyUserId())) {
            userMessage.setReceivedUserId(pComment.getUserId());
        } else if (comment.getPCommentId() != 0 && StrUtil.isNotEmpty(comment.getReplyUserId())) {
            userMessage.setReceivedUserId(comment.getReplyUserId());
        }
        if (!comment.getUserId().equals(userMessage.getReceivedUserId())) {
            userMessageService.insert(userMessage);
        }
    }
}
