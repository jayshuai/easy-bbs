package org.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import org.example.dao.UserInfoDao;
import org.example.entity.EmailCode;
import org.example.dao.EmailCodeDao;
import org.example.entity.UserInfo;
import org.example.entity.constants.Constants;
import org.example.exception.BusinessException;
import org.example.service.EmailCodeService;
import org.example.utils.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.Date;

/**
 * 邮箱验证码(EmailCode)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:33:41
 */
@Service("emailCodeService")
public class EmailCodeServiceImpl implements EmailCodeService {
    @Resource
    private EmailCodeDao emailCodeDao;
    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private CommonUtils commonUtils;


    /**
     * 通过ID查询单条数据
     *
     * @param email 主键
     * @return 实例对象
     */
    @Override
    public EmailCode queryById(String email) {
        return this.emailCodeDao.queryById(email);
    }


    /**
     * 新增数据
     *
     * @param emailCode 实例对象
     * @return 实例对象
     */
    @Override
    public EmailCode insert(EmailCode emailCode) {
        this.emailCodeDao.insert(emailCode);
        return emailCode;
    }

    /**
     * 修改数据
     *
     * @param emailCode 实例对象
     * @return 实例对象
     */
    @Override
    public EmailCode update(EmailCode emailCode) {
        this.emailCodeDao.update(emailCode);
        return this.queryById(emailCode.getEmail());
    }

    /**
     * 通过主键删除数据
     *
     * @param email 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String email) {
        return this.emailCodeDao.deleteById(email) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendEmailCode(String email, Integer type) {
        if (Constants.ZERO.equals(type)) {
            UserInfo userInfo = userInfoDao.queryByEmail(email);
            if (ObjectUtil.isNotEmpty(userInfo)) {
                throw new BusinessException("邮箱已经存在");
            }
        }
        String randomString = commonUtils.getRandomString(Constants.LENGTH_5);
        commonUtils.sendEmail2User(email, randomString);
        emailCodeDao.disableEmailCode(email);
        EmailCode emailCode = new EmailCode();
        emailCode.setCode(randomString);
        emailCode.setEmail(email);
        emailCode.setStatus(Constants.ZERO);//未使用
        emailCode.setCreateTime(new Date());
        emailCodeDao.insert(emailCode);

    }

    @Override
    public void checkCode(String email, String checkCode) {
        EmailCode emailCode = emailCodeDao.queryByEmailAndCode(email, checkCode);
        if (ObjectUtil.isEmpty(emailCode)) {
            throw new BusinessException("邮箱验证码不正确");
        }
        if (emailCode.getStatus() != Constants.ZERO || System.currentTimeMillis() - emailCode.getCreateTime().getTime() > 1000L * 60 * Constants.LENGTH_15) {
            throw new BusinessException("邮箱验证码已失效");
        }
        emailCodeDao.disableEmailCode(email);
    }
}
