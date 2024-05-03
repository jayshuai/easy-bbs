package org.example.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 文章信息(ForumArticle)实体类
 *
 * @author makejava
 * @since 2024-04-04 23:37:38
 */
public class ForumArticle implements Serializable {
    private static final long serialVersionUID = 334215612096957470L;
/**
     * 文章ID
     */
    private String articleId;
/**
     * 板块ID
     */
    private Integer boardId;
/**
     * 板块名称
     */
    private String boardName;
/**
     * 父级板块ID
     */
    private Integer pBoardId;
/**
     * 父板块名称
     */
    private String pBoardName;
/**
     * 用户ID
     */
    private String userId;
/**
     * 昵称
     */
    private String nickName;
/**
     * 最后登录ip地址
     */
    private String userIpAddress;
/**
     * 标题
     */
    private String title;
/**
     * 封面
     */
    private String cover;
/**
     * 内容
     */
    private String content;
/**
     * markdown内容
     */
    private String markdownContent;
/**
     * 0:富文本编辑器 1:markdown编辑器
     */
    private Integer editorType;
/**
     * 摘要
     */
    private String summary;
/**
     * 发布时间
     */
    private Date postTime;
/**
     * 最后更新时间
     */
    private Date lastUpdateTime;
/**
     * 阅读数量
     */
    private Integer readCount;
/**
     * 点赞数
     */
    private Integer goodCount;
/**
     * 评论数
     */
    private Integer commentCount;
/**
     * 0未置顶  1:已置顶
     */
    private Integer topType;
/**
     * 0:没有附件  1:有附件
     */
    private Integer attachmentType;
/**
     * -1已删除 0:待审核  1:已审核 
     */
    private Integer status;


    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public Integer getPBoardId() {
        return pBoardId;
    }

    public void setPBoardId(Integer pBoardId) {
        this.pBoardId = pBoardId;
    }

    public String getPBoardName() {
        return pBoardName;
    }

    public void setPBoardName(String pBoardName) {
        this.pBoardName = pBoardName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserIpAddress() {
        return userIpAddress;
    }

    public void setUserIpAddress(String userIpAddress) {
        this.userIpAddress = userIpAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMarkdownContent() {
        return markdownContent;
    }

    public void setMarkdownContent(String markdownContent) {
        this.markdownContent = markdownContent;
    }

    public Integer getEditorType() {
        return editorType;
    }

    public void setEditorType(Integer editorType) {
        this.editorType = editorType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getTopType() {
        return topType;
    }

    public void setTopType(Integer topType) {
        this.topType = topType;
    }

    public Integer getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(Integer attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

