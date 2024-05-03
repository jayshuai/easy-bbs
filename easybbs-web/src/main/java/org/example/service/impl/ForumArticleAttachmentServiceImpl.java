package org.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.example.dao.ForumArticleAttachmentDao;
import org.example.dao.ForumArticleAttachmentDownloadDao;
import org.example.dao.UserInfoDao;
import org.example.dao.UserMessageDao;
import org.example.entity.*;
import org.example.entity.dto.SessionWebUserDto;
import org.example.entity.enums.*;
import org.example.exception.BusinessException;
import org.example.service.ForumArticleAttachmentService;
import org.example.service.ForumArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 文件信息(ForumArticleAttachment)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:46:14
 */
@Service("forumArticleAttachmentService")
public class ForumArticleAttachmentServiceImpl implements ForumArticleAttachmentService {
    @Resource
    private ForumArticleAttachmentDao forumArticleAttachmentDao;
    @Resource
    private ForumArticleAttachmentDownloadDao forumArticleAttachmentDownloadDao;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private ForumArticleService forumArticleService;

    @Resource
    private UserMessageDao userMessageDao;

    /**
     * 通过ID查询单条数据
     *
     * @param fileId 主键
     * @return 实例对象
     */
    @Override
    public ForumArticleAttachment queryById(String fileId) {
        return this.forumArticleAttachmentDao.queryById(fileId);
    }


    /**
     * 新增数据
     *
     * @param forumArticleAttachment 实例对象
     * @return 实例对象
     */
    @Override
    public ForumArticleAttachment insert(ForumArticleAttachment forumArticleAttachment) {
        this.forumArticleAttachmentDao.insert(forumArticleAttachment);
        return forumArticleAttachment;
    }

    /**
     * 修改数据
     *
     * @param forumArticleAttachment 实例对象
     * @return 实例对象
     */
    @Override
    public ForumArticleAttachment update(ForumArticleAttachment forumArticleAttachment) {
        this.forumArticleAttachmentDao.update(forumArticleAttachment);
        return this.queryById(forumArticleAttachment.getFileId());
    }

    /**
     * 通过主键删除数据
     *
     * @param fileId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String fileId) {
        return this.forumArticleAttachmentDao.deleteById(fileId) > 0;
    }

    @Override
    public List<ForumArticleAttachment> getAttachmentByArticleId(String articleId) {
        if (StrUtil.isEmpty(articleId)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        return forumArticleAttachmentDao.queryByArticleId(articleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ForumArticleAttachment downAttachment(String fileId, SessionWebUserDto userInfoFromSession) {
        ForumArticleAttachment forumArticleAttachment = this.forumArticleAttachmentDao.queryById(fileId);
        if (ObjectUtil.isEmpty(forumArticleAttachment)) {
            throw new BusinessException("附件不存在");
        }
        ForumArticleAttachmentDownload download = null;
        //附件需要的积分大于0,并且不是本人上传的附件
        if (forumArticleAttachment.getIntegral() > 0 && !userInfoFromSession.getUserId().equals(forumArticleAttachment.getUserId())) {
            download = forumArticleAttachmentDownloadDao.queryByIdAndUserId(userInfoFromSession.getUserId(), fileId);
        }
        if (ObjectUtil.isEmpty(download)) {
            UserInfo userInfo = userInfoDao.queryById(userInfoFromSession.getUserId());
            if (userInfo.getCurrentIntegral().compareTo(forumArticleAttachment.getIntegral()) < 0) {
                throw new BusinessException("积分不够");
            }
        }
        ForumArticleAttachmentDownload updateDownloadAttachment = new ForumArticleAttachmentDownload();
        updateDownloadAttachment.setArticleId(forumArticleAttachment.getArticleId());
        updateDownloadAttachment.setFileId(fileId);
        updateDownloadAttachment.setUserId(userInfoFromSession.getUserId());
        updateDownloadAttachment.setDownloadCount(1);
        forumArticleAttachmentDownloadDao.insertOrUpdate(updateDownloadAttachment);
        //更新下载次数
        forumArticleAttachmentDao.updateDownloadCount(fileId);
        if (ObjectUtil.equal(userInfoFromSession.getUserId(), forumArticleAttachment.getUserId()) || ObjectUtil.isNotEmpty(download)) {
            return forumArticleAttachment;
        }
        //扣除下载人积分
        userInfoDao.updateUserIntegral(userInfoFromSession.getUserId(),
                UserIntegralOperTypeEnum.USER_DOWNLOAD_ATTACHMENT, UserIntegralChangeTypeEnum.REDUCE.getChangeType(), forumArticleAttachment.getIntegral());
        //给附件提供者增加积分
        //扣除下载人积分
        userInfoDao.updateUserIntegral(forumArticleAttachment.getUserId(),
                UserIntegralOperTypeEnum.DOWNLOAD_ATTACHMENT, UserIntegralChangeTypeEnum.ADD.getChangeType(), forumArticleAttachment.getIntegral());
        //记录消息
        ForumArticle forumArticle = forumArticleService.queryById(forumArticleAttachment.getArticleId());
        UserMessage userMessage = new UserMessage();
        userMessage.setMessageType(MessageTypeEnum.DOWNLOAD_ATTACHMENT.getType());
        userMessage.setCreateTime(new Date());
        userMessage.setArticleId(forumArticle.getArticleId());
        userMessage.setCommentId(0);
        userMessage.setSendUserId(userInfoFromSession.getUserId());
        userMessage.setSendNickName(userInfoFromSession.getNickName());
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        userMessage.setArticleTitle(forumArticle.getArticleId());
        userMessage.setReceivedUserId(forumArticle.getUserId());
        userMessageDao.insert(userMessage);
        return forumArticleAttachment;
    }
}
