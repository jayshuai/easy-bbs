package org.example.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import org.example.dao.UserInfoDao;
import org.example.dao.UserIntegralRecordDao;
import org.example.dao.UserMessageDao;
import org.example.enetity.dto.LoginDto;
import org.example.entity.UserInfo;
import org.example.entity.UserIntegralRecord;
import org.example.entity.UserMessage;
import org.example.entity.config.WebConfig;
import org.example.entity.constants.Constants;
import org.example.entity.dto.SessionWebUserDto;
import org.example.entity.enums.*;
import org.example.exception.BusinessException;
import org.example.service.EmailCodeService;
import org.example.service.UserInfoService;
import org.example.utils.CommonUtils;
import org.example.utils.JsonUtils;
import org.example.utils.OKHttpUtils;
import org.example.utils.SysCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 用户信息(UserInfo)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:51:14
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private EmailCodeService emailCodeService;
    @Resource
    private CommonUtils commonUtils;

    @Resource
    private UserMessageDao userMessageDao;

    @Resource
    private UserIntegralRecordDao userIntegralRecordDao;

    @Resource
    private WebConfig webComponent;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public UserInfo queryById(String userId) {
        return this.userInfoDao.queryById(userId);
    }


    /**
     * 新增数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserInfo insert(UserInfo userInfo) {
        this.userInfoDao.insert(userInfo);
        return userInfo;
    }

    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserInfo update(UserInfo userInfo) {
        this.userInfoDao.update(userInfo);
        return this.queryById(userInfo.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String userId) {
        return this.userInfoDao.deleteById(userId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String email, String nickName, String emailCode, String password) {
        UserInfo userInfo = userInfoDao.queryByEmail(email);
        if (ObjectUtil.isNotEmpty(userInfo)) {
            throw new BusinessException("邮箱已经存在");
        }
        emailCodeService.checkCode(email, emailCode);
        String userId = IdUtil.getSnowflakeNextIdStr();
        UserInfo insertInfo = new UserInfo();
        insertInfo.setUserId(userId);
        insertInfo.setEmail(email);
        insertInfo.setNickName(nickName);
        insertInfo.setPassword(commonUtils.encodeMd5(password));
        insertInfo.setJoinTime(new Date());
        insertInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        insertInfo.setTotalIntegral(Constants.ZERO);
        insertInfo.setCurrentIntegral(Constants.ZERO);
        userInfoDao.insert(insertInfo);
        //更新用户积分
        updateUserIntegral(userId, UserIntegralOperTypeEnum.REGISTER, UserIntegralChangeTypeEnum.ADD.getChangeType(), Constants.INTEGRAL_5);
        //记录消息
        UserMessage userMessage = new UserMessage();
        userMessage.setReceivedUserId(userId);
        userMessage.setMessageType(MessageTypeEnum.SYS.getType());
        userMessage.setCreateTime(new Date());
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
        userMessage.setMessageContent(SysCacheUtils.getSysSetting().getSysSetting4RegisterDto().getRegisterWelcomeInfo());
        userMessageDao.insert(userMessage);
    }

    /**
     * 更新用户积分
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserIntegral(String userId, UserIntegralOperTypeEnum userIntegralOperTypeEnum, Integer changeType, Integer integral) {
        integral = changeType * integral;
        if (integral == 0) {
            return;
        }
        UserInfo userInfo = userInfoDao.queryById(userId);
        if (UserIntegralChangeTypeEnum.REDUCE.getChangeType().equals(changeType) && userInfo.getCurrentIntegral() + integral < 0) {
            integral = changeType * userInfo.getCurrentIntegral();
        }
        UserIntegralRecord record = new UserIntegralRecord();
        record.setUserId(userId);
        record.setOperType(userIntegralOperTypeEnum.getOperType());
        record.setIntegral(integral);
        record.setCreateTime(new Date());
        this.userIntegralRecordDao.insert(record);

        int count = this.userInfoDao.updateIntegral(userId, integral);
        if (count == 0) {
            throw new BusinessException("更新用户积分失败");
        }
    }

    @Override
    public SessionWebUserDto login(String email, String password, String ip) {
        password = MD5.create().digestHex(password);
        UserInfo userInfo = this.userInfoDao.queryByEmail(email);
        if (ObjectUtil.isEmpty(userInfo) || !password.equals(userInfo.getPassword())) {
            throw new BusinessException("账号或密码错误");
        }
        if (UserStatusEnum.DISABLED.getStatus() == userInfo.getStatus()) {
            throw new BusinessException("账号已被禁用");
        }
        String ipAddress = getIpAddress(ip);
        UserInfo updateInfo = new UserInfo();
        updateInfo.setLastLoginTime(new Date());
        updateInfo.setLastLoginIp(ip);
        updateInfo.setUserId(userInfo.getUserId());
        updateInfo.setLastLoginIpAddress(ipAddress);
        this.userInfoDao.update(updateInfo);

        SessionWebUserDto sessionWebUserDto = new SessionWebUserDto();
        sessionWebUserDto.setNickName(userInfo.getNickName());
        sessionWebUserDto.setProvince(ipAddress);
        sessionWebUserDto.setUserId(userInfo.getUserId());
        if (StrUtil.isNotEmpty(webComponent.getAdminEmails()) && ArrayUtil.contains(webComponent.getAdminEmails().split(","), userInfo.getEmail())) {
            sessionWebUserDto.setIsAdmin(true);
        }
        {
            sessionWebUserDto.setIsAdmin(false);
        }
        return sessionWebUserDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPwd(LoginDto loginDto, String emailCode) {
        UserInfo userInfo1 = this.userInfoDao.queryByEmail(loginDto.getEmail());
        if (ObjectUtil.isEmpty(userInfo1)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        emailCodeService.checkCode(loginDto.getEmail(), emailCode);
        UserInfo updateInfo = new UserInfo();
        updateInfo.setLastLoginTime(new Date());
        updateInfo.setPassword(MD5.create().digestHex(loginDto.getPassword()));
        this.userInfoDao.update(updateInfo);
    }

    public String getIpAddress(String ip) {
        Map<String, String> addressInfo;
        try {
            String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip;
            String responseJson = OKHttpUtils.getRequest(url);
            if (responseJson == null) {
                return "未知";
            }
            addressInfo = JsonUtils.convertJson2obj(responseJson, Map.class);
            return addressInfo.get("pro");
        } catch (Exception e) {
            logger.error("获取IP地址失败", e);
        }
        return "未知";
    }
}
