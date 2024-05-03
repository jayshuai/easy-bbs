package org.example.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.example.annotation.GlobalInterceptor;
import org.example.entity.*;
import org.example.entity.config.WebConfig;
import org.example.entity.constants.Constants;
import org.example.entity.dto.ForumArticleDto;
import org.example.entity.dto.PostArticleDto;
import org.example.entity.dto.SessionWebUserDto;
import org.example.entity.enums.ArticleStatusEnum;
import org.example.entity.enums.EditorTypeEnum;
import org.example.entity.enums.OperRecordOpTypeEnum;
import org.example.entity.enums.ResponseCodeEnum;
import org.example.entity.vo.*;
import org.example.exception.BusinessException;
import org.example.service.*;
import org.example.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("forum")
public class ForumArticleController extends ABaseController {
    private static final Logger logger = LoggerFactory.getLogger(ForumArticleController.class);
    @Resource
    private ForumArticleService forumArticleService;
    @Resource
    private ForumArticleAttachmentService attachmentService;

    @Resource
    private LikeRecordService likeRecordService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private ForumArticleAttachmentDownloadService downloadService;

    @Resource
    private ForumBoardService forumBoardService;

    @Resource
    private WebConfig webConfig;

    @PostMapping("/loadArticle")
    public ResponseVO loadArticle(HttpSession session, @RequestBody ForumArticleDto forumArticleDto) {
        SessionWebUserDto userInfoFromSession = getUserInfoFromSession(session);
        if (ObjectUtil.isNotEmpty(userInfoFromSession)) {
            forumArticleDto.setCurrentUserId(userInfoFromSession.getUserId());
        } else {
            forumArticleDto.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        }
        PaginationResultVO resultVO = forumArticleService.findListByPage(forumArticleDto);
        return getSuccessResponseVO(convert2PaginationVO(resultVO, ForumArticle.class));
    }

    @GetMapping("/getArticleDetail")
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO getArticleDetail(HttpSession session, @NotNull(message = "文章id不能为空") @RequestParam("id") String articleId) {
        SessionWebUserDto userInfoFromSession = getUserInfoFromSession(session);
        ForumArticle forumArticle = forumArticleService.readArticle(articleId);
        if (Objects.equals(ArticleStatusEnum.NO_AUDIT.getStatus(), forumArticle.getStatus())
                && (userInfoFromSession == null || !userInfoFromSession.getUserId().equals(forumArticle.getUserId()))) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        FormArticleDetailVo detailVo = new FormArticleDetailVo();
        detailVo.setForumArticleVo(BeanUtil.copyProperties(forumArticle, ForumArticleVo.class));
        //有附件
        if (forumArticle.getAttachmentType() == Constants.ONE) {
            List<ForumArticleAttachment> attachment = attachmentService.getAttachmentByArticleId(articleId);
            if (CollectionUtil.isNotEmpty(attachment)) {
                detailVo.setAttachmentVo(BeanUtil.copyProperties(attachment.get(0), ForumArticleAttachmentVo.class));
            }
        }
        //objectId=articleId
        //TODO 是否已经点赞
        if (userInfoFromSession != null) {
            LikeRecord likeRecord = likeRecordService.getRecordByObjectIdAndUserIdAndOrderType(articleId, userInfoFromSession.getUserId(), OperRecordOpTypeEnum.ARTICLE_LIKE.getType());
            if (likeRecord != null) {
                detailVo.setHaveLike(true);
            }
        }
        return getSuccessResponseVO(detailVo);
    }

    @RequestMapping("/doLike")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO doLike(HttpSession session, @NotNull String articleId) {
        SessionWebUserDto userInfoFromSession = getUserInfoFromSession(session);
        likeRecordService.doLike(articleId, userInfoFromSession.getUserId(), userInfoFromSession.getNickName(), OperRecordOpTypeEnum.ARTICLE_LIKE);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("getUserDownLoadInfo")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO getUserDownLoadInfo(HttpSession session, @NotNull(message = "文件id不能为空") String fileId) {
        SessionWebUserDto userInfoFromSession = getUserInfoFromSession(session);
        UserInfo userInfo = userInfoService.queryById(userInfoFromSession.getUserId());
        UserDownloadInfoVo userVo = new UserDownloadInfoVo();
        userVo.setUserIntegral(userInfo.getCurrentIntegral());
        ForumArticleAttachmentDownload download = downloadService.getFileDownLoadByUserIdAndFileId(userInfo.getUserId(), fileId);
        if (download != null) {
            userVo.setHaveDownLoad(true);
        }
        return getSuccessResponseVO(userInfo);
    }

    @RequestMapping("attachmentDownLoad")
    @GlobalInterceptor(checkLogin = true)
    public void attachmentDownLoad(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                   @NotNull(message = "文件id不能为空")
                                   String fileId) {
        ForumArticleAttachment attachment = attachmentService.downAttachment(fileId, getUserInfoFromSession(session));
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String downloadFileName = attachment.getFileName();
        String filePath = webConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_ATTACHMENT
                + attachment.getFilePath();
        File file = new File(filePath);
        try {
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            response.setContentType("application/x-msdownload; charset=UTF-8");
            //解决中文乱码问题
            if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") > 0) {
                downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
            } else {
                downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadFileName + "\"");
            byte[] byteData = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(byteData)) != -1) {
                outputStream.write(byteData, 0, len);//write

            }
            outputStream.flush();
        } catch (Exception e) {
            logger.error("下载异常", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("IO异常", e);
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }

            } catch (IOException e) {
                logger.error("IO异常", e);
            }
        }
    }

    @RequestMapping("loadBorder4Post")
//  @GlobalInterceptor(checkLogin = true);
    public ResponseVO loadBorder4Post(HttpSession session) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        Integer postType = null;
        if (!userDto.getIsAdmin()) {
            postType = Constants.ONE;
        }
        return getSuccessResponseVO(forumBoardService.getBoardTree(postType));
    }


    @PostMapping("postArticle")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO postArticle(HttpSession session, @RequestBody PostArticleDto postArticleDto) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        String title = StringTools.escapeHtml(postArticleDto.getTitle());
        ForumArticle forumArticle = new ForumArticle();
        forumArticle.setUserId(userDto.getUserId());
        forumArticle.setPBoardId(postArticleDto.getPBoardId());
        forumArticle.setBoardId(postArticleDto.getBoardId());
        forumArticle.setSummary(postArticleDto.getSummary());
        forumArticle.setTitle(title);
        forumArticle.setContent(postArticleDto.getContent());
        EditorTypeEnum typeEnum = EditorTypeEnum.getByType(postArticleDto.getEditorType());
        if (ObjectUtil.isEmpty(typeEnum)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (EditorTypeEnum.MARKDOWN.getType().equals(postArticleDto.getEditorType()) && StrUtil.isEmpty(postArticleDto.getMarkdownContent())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        forumArticle.setMarkdownContent(postArticleDto.getMarkdownContent());
        forumArticle.setNickName(userDto.getNickName());
        forumArticle.setUserIpAddress(userDto.getProvince());
        //附件信息
        ForumArticleAttachment forumArticleAttachment = new ForumArticleAttachment();
        forumArticleAttachment.setIntegral(postArticleDto.getIntegral() == null ? 0 : postArticleDto.getIntegral());
        forumArticleService.postArticle(userDto.getIsAdmin(), forumArticle, forumArticleAttachment, postArticleDto.getCover(), postArticleDto.getAttachment());
        return getSuccessResponseVO(forumArticle.getArticleId());
    }

    @PostMapping("articleDetail4Update")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO articleDetail4Update(HttpSession session, @NotNull(message = "文章id不能为空") String articleId) {
        SessionWebUserDto userInfoFromSession = getUserInfoFromSession(session);
        ForumArticle forumArticle = forumArticleService.queryById(articleId);
        if (forumArticle == null || !forumArticle.getUserId().equals(userInfoFromSession.getUserId())) {
            throw new BusinessException("文章不存在或者无权修改");
        }
        ForumArticleVo forumArticleVo = new ForumArticleVo();
        BeanUtil.copyProperties(forumArticle, forumArticleVo);
        FormArticleDetailVo formArticleDetailVo = new FormArticleDetailVo();
        formArticleDetailVo.setForumArticleVo(forumArticleVo);

        return getSuccessResponseVO(true);
    }

    @PostMapping("updateArticle")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO updateArticle(HttpSession session, @RequestBody PostArticleDto postArticleDto, Integer attachmentType, String articleId) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        ForumArticle forumArticle = new ForumArticle();
        forumArticle.setArticleId(articleId);
        forumArticle.setBoardId(postArticleDto.getBoardId());
        forumArticle.setPBoardId(postArticleDto.getPBoardId());
        forumArticle.setTitle(postArticleDto.getTitle());
        forumArticle.setContent(postArticleDto.getContent());
        forumArticle.setMarkdownContent(postArticleDto.getMarkdownContent());
        forumArticle.setEditorType(postArticleDto.getEditorType());
        forumArticle.setSummary(postArticleDto.getSummary());
        forumArticle.setUserIpAddress(userDto.getProvince());
        forumArticle.setUserId(userDto.getUserId());
        forumArticle.setAttachmentType(attachmentType);
        //附加信息
        ForumArticleAttachment forumArticleAttachment = new ForumArticleAttachment();
        forumArticleAttachment.setIntegral(postArticleDto.getIntegral() == null ? 0 : postArticleDto.getIntegral());
        forumArticleService.updateArticle(userDto.getIsAdmin(), forumArticle, forumArticleAttachment, postArticleDto.getCover(), postArticleDto.getAttachment());
        return getSuccessResponseVO(null);
    }

}