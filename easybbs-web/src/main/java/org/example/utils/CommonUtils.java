package org.example.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.entity.config.WebConfig;
import org.example.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class CommonUtils {
    @Resource
    private WebConfig webComponent;

    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    @Resource
    private JavaMailSender javaMailSender;

    public String getRandomString(Integer count) {
        String random = RandomStringUtils.random(count, true, true);
        return random;
    }

    public void sendEmail2User(String toEmail, String code) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //邮件发送人
            helper.setFrom(webComponent.getSendUserName());
            //邮件收件人
            helper.setTo(toEmail);
            //标题
            helper.setSubject("注册邮件验证码");
            //正文
            helper.setText("邮箱验证码为" + code);
            helper.setSentDate(new Date());
            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error("发送邮件失败:{}", e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public String encodeMd5(String password) {
        return StrUtil.isEmpty(password) ? null : MD5.create().digestHex(password);
    }
}
