package org.example.controller;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.example.entity.ForumComment;
import org.example.entity.constants.Constants;
import org.example.entity.dto.SessionWebUserDto;
import org.example.entity.enums.ArticleStatusEnum;
import org.example.entity.enums.ComentTopTypeEnum;
import org.example.entity.enums.PageSizeEnum;
import org.example.entity.enums.ResponseCodeEnum;
import org.example.entity.vo.ResponseVO;
import org.example.exception.BusinessException;
import org.example.service.ForumCommentService;
import org.example.service.LikeRecordService;
import org.example.utils.StringTools;
import org.example.utils.SysCacheUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/comment")
public class ForumCommentController extends ABaseController {

    @Resource
    private ForumCommentService forumCommentService;

    @Resource
    private LikeRecordService likeRecordService;

    @RequestMapping("loadComment")
    public ResponseVO loadComment(HttpSession session, @NotNull(message = "文章id不能为空")
    String articleId, Integer pageNo, Integer orderType) {
        final String ORDER_TYPE0 = "good_count desc,comment_id asc";
        final String ORDER_TYPE1 = "comment_id desc";
        //关闭评论
        if (!SysCacheUtils.getSysSetting().getSysSetting4CommentDto().getCommentOpen()) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        Map<String, Object> commentQuery = new HashMap<>();
        String orderBy = orderType == null || orderType == Constants.ZERO ? ORDER_TYPE0 : ORDER_TYPE1;
        commentQuery.put("orderBy", "top_type desc" + orderBy);
        commentQuery.put("articleId", articleId);
        SessionWebUserDto userInfoFromSession = getUserInfoFromSession(session);
        if (userInfoFromSession != null) {
            commentQuery.put("QueryLike", true);
            commentQuery.put("currentUserId", userInfoFromSession.getUserId());
        } else {
            commentQuery.put("status", ArticleStatusEnum.AUDIT.getStatus());
        }
        commentQuery.put("pageNo", pageNo);
        commentQuery.put("pageSize", PageSizeEnum.PAGE_SIZE_20.getSize());
        commentQuery.put("commentId", 0);
        List<ForumComment> list = forumCommentService.findCountByParam(commentQuery);

        return getSuccessResponseVO(list);
    }

    @RequestMapping("postComment")
    public ResponseVO postComment(HttpSession session, @NotNull(message = "文章id不能为空")
    String articleId, Integer pCommentId, @NotNull(message = "评论不能为空") @Min(5) @Max(100) String content,
                                  MultipartFile image, String replayUserId) {
        //关闭评论
        if (!SysCacheUtils.getSysSetting().getSysSetting4CommentDto().getCommentOpen()) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (image == null && StrUtil.isEmpty(content)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        ForumComment forumComment = new ForumComment();
        content = StringTools.escapeHtml(content);
        forumComment.setUserId(userDto.getUserId());
        forumComment.setNickName(userDto.getNickName());
        forumComment.setPCommentId(pCommentId);
        forumComment.setArticleId(articleId);
        forumComment.setContent(content);
        forumComment.setReplyUserId(replayUserId);
        forumComment.setTopType(ComentTopTypeEnum.NO_TOP.getType());
        forumCommentService.postComment(forumComment, image);
        if (pCommentId != 0) {
            Map<String, String> query = new HashMap<>();
            query.put("ArticleId", articleId);
            query.put("pCommentId", pCommentId.toString());
            query.put("OrderBy", "top_type desc,comment_id asc");
            List<ForumComment> child = forumCommentService.findListByParam(query);
            return getSuccessResponseVO(child);
        }
        return getSuccessResponseVO(forumComment);
    }
}
