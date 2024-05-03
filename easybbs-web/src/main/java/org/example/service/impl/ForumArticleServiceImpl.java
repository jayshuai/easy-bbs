package org.example.service.impl;

import cn.hutool.core.util.StrUtil;
import org.example.dao.ForumArticleAttachmentDao;
import org.example.dao.ForumArticleDao;
import org.example.entity.ForumArticle;
import org.example.entity.ForumArticleAttachment;
import org.example.entity.ForumBoard;
import org.example.entity.constants.Constants;
import org.example.entity.dto.FileUploadDto;
import org.example.entity.dto.ForumArticleDto;
import org.example.entity.dto.SysSetting4AuditDot;
import org.example.entity.enums.*;
import org.example.entity.vo.PaginationResultVO;
import org.example.exception.BusinessException;
import org.example.service.ForumArticleService;
import org.example.service.ForumBoardService;
import org.example.service.UserInfoService;
import org.example.utils.FileUtils;
import org.example.utils.ImageUtils;
import org.example.utils.StringTools;
import org.example.utils.SysCacheUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 文章信息(ForumArticle)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:37:39
 */
@Service("forumArticleService")
public class ForumArticleServiceImpl implements ForumArticleService {
    @Resource
    private ForumArticleDao forumArticleDao;

    @Resource
    private ForumBoardService forumBoardService;

    @Resource
    private FileUtils fileUtils;

    @Resource
    private ForumArticleAttachmentDao forumArticleAttachmentDao;

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ImageUtils imageUtils;

    /**
     * 通过ID查询单条数据
     *
     * @param articleId 主键
     * @return 实例对象
     */
    @Override
    public ForumArticle queryById(String articleId) {
        return this.forumArticleDao.queryById(articleId);
    }


    /**
     * 新增数据
     *
     * @param forumArticle 实例对象
     * @return 实例对象
     */
    @Override
    public ForumArticle insert(ForumArticle forumArticle) {
        this.forumArticleDao.insert(forumArticle);
        return forumArticle;
    }

    /**
     * 修改数据
     *
     * @param forumArticle 实例对象
     * @return 实例对象
     */
    @Override
    public ForumArticle update(ForumArticle forumArticle) {
        this.forumArticleDao.update(forumArticle);
        return this.queryById(forumArticle.getArticleId());
    }

    /**
     * 通过主键删除数据
     *
     * @param articleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String articleId) {
        return this.forumArticleDao.deleteById(articleId) > 0;
    }

    @Override
    public PaginationResultVO findListByPage(ForumArticleDto forumArticleDto) {
        ArticleOrderTypeEnum orderTypeEnum = ArticleOrderTypeEnum.getByType(forumArticleDto.getOrderType());
        String sqlString;
        if (orderTypeEnum != null) {
            sqlString = orderTypeEnum.getOrderSql();
        } else {
            sqlString = ArticleOrderTypeEnum.HOT.getOrderSql();
        }
        PaginationResultVO paginationResultVO = new PaginationResultVO<>();
        ForumArticle queryArticle = new ForumArticle();
        queryArticle.setUserId(forumArticleDto.getCurrentUserId());
        queryArticle.setBoardId(Integer.valueOf(forumArticleDto.getBoardId()));
        queryArticle.setPBoardId(Integer.valueOf(forumArticleDto.getPBoardId()));
        queryArticle.setStatus(forumArticleDto.getStatus());
        Integer start = ((forumArticleDto.getPageNo() - 1) * PageSizeEnum.PAGE_SIZE_10.getSize() + PageSizeEnum.PAGE_SIZE_10.getSize());
        List<ForumArticle> forumArticleList = forumArticleDao.
                queryPageByConditon(queryArticle, sqlString, start, PageSizeEnum.PAGE_SIZE_10.getSize());
        Long count = forumArticleDao.count(queryArticle);
        paginationResultVO.setList(forumArticleList);
        paginationResultVO.setPageNo(forumArticleDto.getPageNo());
        paginationResultVO.setPageSize(PageSizeEnum.PAGE_SIZE_10.getSize());
        paginationResultVO.setTotalCount(count.intValue());
        return paginationResultVO;
    }

    @Override
    public ForumArticle readArticle(String articleId) {
        ForumArticle forumArticle = this.queryById(articleId);
        if (forumArticle == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        //如果是已审核状态
        if (ArticleStatusEnum.AUDIT.getStatus().equals(forumArticle.getStatus())) {
            this.forumArticleDao.updateArticleCount(UpdateArticleCountTypeEnum.READ_COUNT.getType(), Constants.ONE, articleId);
        }
        return forumArticle;
    }

    @Override
    public void postArticle(Boolean isAdmin, ForumArticle forumArticle, ForumArticleAttachment forumArticleAttachment, MultipartFile cover, MultipartFile attachment) {
        resetBoardInfo(isAdmin, forumArticle);
        String articleId = StringTools.getRandomString(Constants.LENGTH_15);
        Date date = new Date();
        forumArticle.setArticleId(articleId);
        forumArticle.setPostTime(date);
        forumArticle.setLastUpdateTime(date);
        if (cover != null) {
            FileUploadDto fileUploadDto = fileUtils.uploadFile2Local(cover, Constants.FILE_FOLDER_IMAGE, FileUploadTypeEnum.ARTICLE_COVER);
            forumArticle.setCover(fileUploadDto.getLocalPath());
        }
        if (attachment != null) {
            uploadAttachment(forumArticle, forumArticleAttachment, attachment, false);
            forumArticle.setAttachmentType(ArticleAttachmentTypeEnum.YES.getType());
        } else {
            forumArticle.setAttachmentType(ArticleAttachmentTypeEnum.NO.getType());
        }
        //审核信息
        if (isAdmin) {
            forumArticle.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        } else {
            SysSetting4AuditDot sysSetting4AuditDot = SysCacheUtils.getSysSetting().getSysSetting4AuditDot();
            forumArticle.setStatus(sysSetting4AuditDot.getPostAudit() ? ArticleStatusEnum.NO_AUDIT.getStatus() : ArticleStatusEnum.AUDIT.getStatus());
        }
        String content = forumArticle.getContent();
        //替换图片
        if (StrUtil.isNotEmpty(content)) {
            String month = imageUtils.resetImageHtml(content);
            String replaceMonth = "/" + month + "/";
            content = content.replace(Constants.FILE_FOLDER_TEMP, replaceMonth);
            forumArticle.setContent(content);
            String markDownContent = forumArticle.getMarkdownContent();
            if (!StrUtil.isEmpty(markDownContent)) {
                markDownContent = markDownContent.replace(Constants.FILE_FOLDER_TEMP, replaceMonth);
                forumArticle.setMarkdownContent(markDownContent);
            }
        }
        this.forumArticleDao.insert(forumArticle);
        Integer postIntegral = SysCacheUtils.getSysSetting().getSysSetting4PostDto().getPostIntegral();
        if (postIntegral > 0 && ArticleStatusEnum.AUDIT.getStatus().equals(forumArticle.getStatus())) {
            userInfoService.updateUserIntegral(forumArticle.getUserId(), UserIntegralOperTypeEnum.POST_COMMENT, UserIntegralChangeTypeEnum.ADD.getChangeType(), postIntegral);
        }

    }

    @Override
    public void updateArticle(Boolean isAdmin, ForumArticle forumArticle, ForumArticleAttachment forumArticleAttachment, MultipartFile cover, MultipartFile attachment) {
        ForumArticle dnInfo = forumArticleDao.queryById(forumArticle.getArticleId());
    }

    private void resetBoardInfo(Boolean isAdmin, ForumArticle forumArticle) {
        ForumBoard pBoard = forumBoardService.queryById(forumArticle.getPBoardId());
        if (pBoard == null || Objects.equals(pBoard.getPostType(), Constants.ZERO) && !isAdmin) {
            throw new BusinessException("一级板块不存在");
        }
        forumArticle.setPBoardName(pBoard.getBoardName());
        if (forumArticle.getBoardId() != null && forumArticle.getBoardId() != 0) {
            ForumBoard board = forumBoardService.queryById(forumArticle.getBoardId());
            if (board == null || Objects.equals(board.getPostType(), Constants.ZERO) && !isAdmin) {
                throw new BusinessException("二级板块不存在");
            }
            forumArticle.setBoardName(board.getBoardName());
        } else {
            forumArticle.setBoardId(0);
            forumArticle.setBoardName("");
        }
    }

    public void uploadAttachment(ForumArticle article, ForumArticleAttachment attachment, MultipartFile file, Boolean isUpdate) {
        Integer allowSizeMb = SysCacheUtils.getSysSetting().getSysSetting4PostDto().getAttachmentSize();
        Long allowSize = allowSizeMb * Constants.FILE_SIZE_1M;
        if (file.getSize() > allowSize) {
            throw new BusinessException("附件最大只能上传+" + allowSize + "MB");
        }
        //修改
        if (isUpdate) {

        }
        FileUploadDto fileUploadDto = fileUtils.uploadFile2Local(file, Constants.FILE_FOLDER_ATTACHMENT, FileUploadTypeEnum.ARTICLE_ATTACHMENT);
        attachment.setFileId(StringTools.getRandomNumber(Constants.LENGTH_15));
        attachment.setArticleId(article.getArticleId());
        attachment.setFileName(fileUploadDto.getOriginalFileName());
        attachment.setFilePath(fileUploadDto.getLocalPath());
        attachment.setFileSize(file.getSize());
        attachment.setDownloadCount(Constants.ZERO);
        attachment.setFileType(AttachmentFileTypeEnum.ZIP.getType());
        attachment.setUserId(article.getUserId());
        forumArticleAttachmentDao.insert(attachment);
    }
}
