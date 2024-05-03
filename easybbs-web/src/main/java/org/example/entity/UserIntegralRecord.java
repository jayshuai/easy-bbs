package org.example.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户积分记录表(UserIntegralRecord)实体类
 *
 * @author makejava
 * @since 2024-04-04 23:51:31
 */
public class UserIntegralRecord implements Serializable {
    private static final long serialVersionUID = -49375651363039377L;
/**
     * 记录ID
     */
    private Integer recordId;
/**
     * 用户ID
     */
    private String userId;
/**
     * 操作类型
     */
    private Integer operType;
/**
     * 积分
     */
    private Integer integral;
/**
     * 创建时间
     */
    private Date createTime;


    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getOperType() {
        return operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

