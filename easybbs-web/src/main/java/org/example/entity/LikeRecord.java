package org.example.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 点赞记录(LikeRecord)实体类
 *
 * @author makejava
 * @since 2024-04-04 23:50:46
 */
public class LikeRecord implements Serializable {
    private static final long serialVersionUID = -70614311562148263L;
/**
     * 自增ID
     */
    private Integer opId;
/**
     * 操作类型0:文章点赞 1:评论点赞
     */
    private Integer opType;
/**
     * 主体ID
     */
    private String objectId;
/**
     * 用户ID
     */
    private String userId;
/**
     * 发布时间
     */
    private Date createTime;
/**
     * 主体作者ID
     */
    private String authorUserId;


    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(String authorUserId) {
        this.authorUserId = authorUserId;
    }

}

