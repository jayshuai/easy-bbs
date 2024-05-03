package org.example.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户信息(UserInfo)实体类
 *
 * @author makejava
 * @since 2024-04-04 23:51:14
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 533787586211412113L;
/**
     * 用户ID
     */
    private String userId;
/**
     * 昵称
     */
    private String nickName;
/**
     * 邮箱
     */
    private String email;
/**
     * 密码
     */
    private String password;
/**
     * 0:女 1:男
     */
    private Integer sex;
/**
     * 个人描述
     */
    private String personDescription;
/**
     * 加入时间
     */
    private Date joinTime;
/**
     * 最后登录时间
     */
    private Date lastLoginTime;
/**
     * 最后登录IP
     */
    private String lastLoginIp;
/**
     * 最后登录ip地址
     */
    private String lastLoginIpAddress;
/**
     * 积分
     */
    private Integer totalIntegral;
/**
     * 当前积分
     */
    private Integer currentIntegral;
/**
     * 0:禁用 1:正常
     */
    private Integer status;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPersonDescription() {
        return personDescription;
    }

    public void setPersonDescription(String personDescription) {
        this.personDescription = personDescription;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginIpAddress() {
        return lastLoginIpAddress;
    }

    public void setLastLoginIpAddress(String lastLoginIpAddress) {
        this.lastLoginIpAddress = lastLoginIpAddress;
    }

    public Integer getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(Integer totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public Integer getCurrentIntegral() {
        return currentIntegral;
    }

    public void setCurrentIntegral(Integer currentIntegral) {
        this.currentIntegral = currentIntegral;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

