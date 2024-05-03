package org.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import org.example.dao.ForumArticleDao;
import org.example.dao.LikeRecordDao;
import org.example.dao.UserMessageDao;
import org.example.entity.ForumArticle;
import org.example.entity.LikeRecord;
import org.example.entity.UserMessage;
import org.example.entity.constants.Constants;
import org.example.entity.enums.MessageStatusEnum;
import org.example.entity.enums.MessageTypeEnum;
import org.example.entity.enums.OperRecordOpTypeEnum;
import org.example.entity.enums.UpdateArticleCountTypeEnum;
import org.example.exception.BusinessException;
import org.example.service.LikeRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 点赞记录(LikeRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:50:46
 */
@Service("likeRecordService")
public class LikeRecordServiceImpl implements LikeRecordService {
    @Resource
    private LikeRecordDao likeRecordDao;

    @Resource
    private UserMessageDao userMessageDao;

    @Resource
    private ForumArticleDao articleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param opId 主键
     * @return 实例对象
     */
    @Override
    public LikeRecord queryById(Integer opId) {
        return this.likeRecordDao.queryById(opId);
    }


    /**
     * 新增数据
     *
     * @param likeRecord 实例对象
     * @return 实例对象
     */
    @Override
    public LikeRecord insert(LikeRecord likeRecord) {
        this.likeRecordDao.insert(likeRecord);
        return likeRecord;
    }

    /**
     * 修改数据
     *
     * @param likeRecord 实例对象
     * @return 实例对象
     */
    @Override
    public LikeRecord update(LikeRecord likeRecord) {
        this.likeRecordDao.update(likeRecord);
        return this.queryById(likeRecord.getOpId());
    }

    /**
     * 通过主键删除数据
     *
     * @param opId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer opId) {
        return this.likeRecordDao.deleteById(opId) > 0;
    }

    @Override
    public LikeRecord getRecordByObjectIdAndUserIdAndOrderType(String objectId, String userId, Integer type) {
        return likeRecordDao.queryRecordByObjectIdAndUserIdAndOrderType(objectId, userId, type);
    }

    @Override
    public void doLike(String objectId, String userId, String nickName, OperRecordOpTypeEnum opTypeEnum) {
        UserMessage userMessage = new UserMessage();
        userMessage.setCreateTime(new Date());
        LikeRecord likeRecord = null;
        switch (opTypeEnum) {
            case ARTICLE_LIKE:
                ForumArticle forumArticle = articleDao.queryById(objectId);
                if (ObjectUtil.isEmpty(forumArticle)) {
                    throw new BusinessException("文章不存在");
                }
                likeRecord = articleLike(objectId, userId, opTypeEnum, forumArticle);
                userMessage.setArticleId(likeRecord.getObjectId());
                userMessage.setArticleTitle(forumArticle.getTitle());
                userMessage.setMessageType(MessageTypeEnum.ARTICLE_LIKE.getType());
                userMessage.setCommentId(Constants.ZERO);
                userMessage.setReceivedUserId(forumArticle.getUserId());
                break;
            case COMMENT_LIKE:
                break;
        }
        userMessage.setSendUserId(userId);
        userMessage.setSendNickName(nickName);
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());//未读
        if (ObjectUtil.isEmpty(likeRecord) && !userId.equals(userMessage.getReceivedUserId())) {
            UserMessage dbMessage = userMessageDao.
                    queryByArticleIdAndCommentIdAndSendUserIdAndMessageType
                            (userMessage.getArticleId(), userMessage.getCommentId(), userMessage.getSendUserId(),
                                    userMessage.getMessageType());
            if (dbMessage == null) {
                userMessageDao.insert(userMessage);
            }

        }
    }

    @Transactional(rollbackFor = Exception.class)
    public LikeRecord articleLike(String objectId, String userId, OperRecordOpTypeEnum opTypeEnum, ForumArticle forumArticle) {
        LikeRecord likeRecord = this.likeRecordDao.queryRecordByObjectIdAndUserIdAndOrderType(objectId, userId, opTypeEnum.getType());
        if (likeRecord != null) {
            this.likeRecordDao.deleteByObjectIdAndUserIdAndOpType(objectId, userId, opTypeEnum.getType());
            articleDao.updateArticleCount(UpdateArticleCountTypeEnum.GOOD_COUNT.getType(), -1, objectId);//objectId=articleId
        } else {
            likeRecord = new LikeRecord();
            likeRecord.setObjectId(objectId);
            likeRecord.setUserId(userId);
            likeRecord.setCreateTime(new Date());
            likeRecord.setOpType(opTypeEnum.getType());
            likeRecord.setOpId(opTypeEnum.getType());
            likeRecord.setAuthorUserId(forumArticle.getUserId());
            likeRecordDao.insert(likeRecord);
            articleDao.updateArticleCount(UpdateArticleCountTypeEnum.GOOD_COUNT.getType(), Constants.ONE, objectId);//objectId=articleId
        }
        return likeRecord;
    }
}
