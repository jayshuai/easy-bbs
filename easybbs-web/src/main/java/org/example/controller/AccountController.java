package org.example.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.example.entity.constants.Constants;
import org.example.entity.dto.CreateImageCode;
import org.example.enetity.dto.LoginDto;
import org.example.entity.dto.SessionWebUserDto;
import org.example.entity.dto.SysSetting4CommentDto;
import org.example.entity.dto.SysSettingDto;
import org.example.entity.enums.ResponseCodeEnum;
import org.example.entity.vo.ResponseVO;
import org.example.exception.BusinessException;
import org.example.service.EmailCodeService;
import org.example.service.UserInfoService;
import org.example.utils.SysCacheUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController("AccountController")
@RequestMapping("account")
public class AccountController extends ABaseController {
    @Resource
    private EmailCodeService emailCodeService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 验证码
     */
    @RequestMapping("/checkCode")
    public void checkCode(HttpServletResponse response, HttpSession session, Integer type) throws IOException {
        CreateImageCode code = new CreateImageCode(130, 38, 5, 10);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code1 = code.getCode();
        if (type == null || type == 0) {//登录注册
            session.setAttribute(Constants.CHECK_CODE_KEY, code1);
        } else {//获取邮箱
            session.setAttribute(Constants.CHECK_CODE_KEY_EMAIL, code1);
        }
        code.write(response.getOutputStream());
    }

    @RequestMapping("/sendEmailCode")
    public ResponseVO sendEmailCode(String email, String checkCode, Integer type, HttpSession session) {
        try {
            if (StrUtil.isBlank(email) || StrUtil.isBlank(checkCode) || type == null) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            if (ObjectUtil.notEqual(checkCode, (String) session.getAttribute(Constants.CHECK_CODE_KEY_EMAIL))) {
                throw new BusinessException("图片验证码不正确");
            }
            emailCodeService.sendEmailCode(email, type);
            return getSuccessResponseVO(null);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY_EMAIL);
        }

    }

    @RequestMapping("/register")
    public ResponseVO register(HttpSession session, @NotNull String nickName, @NotNull @Max(150) @Email String email, @NotNull String emailCode, @NotNull String password, @NotNull String checkCode) {

        try {
            if (StrUtil.isBlank(checkCode) || StrUtil.isBlank(nickName) || StrUtil.isBlank(emailCode) || StrUtil.isBlank(password) || StrUtil.isBlank(email)) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            if (ObjectUtil.notEqual(checkCode, (String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码不正确");
            }
            userInfoService.register(email, nickName, emailCode, password);
            return getSuccessResponseVO(null);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    @RequestMapping("/login")
    public ResponseVO login(HttpSession session, @RequestBody @Valid LoginDto loginDto, HttpServletRequest request) {
        try {
            if (ObjectUtil.notEqual(loginDto.getCheckCode(), (String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码不正确");
            }
            String ipAddress = getIpAddress(request);
            SessionWebUserDto sessionWebUserDto = userInfoService.login(loginDto.getEmail(), loginDto.getPassword(), ipAddress);
            session.setAttribute(Constants.SESSION_KEY, sessionWebUserDto);
            return getSuccessResponseVO(sessionWebUserDto);
        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }
    }

    @RequestMapping("/getUserInfo")
    public ResponseVO getUserInfo(HttpSession session) {
        SessionWebUserDto userInfoFromSession = getUserInfoFromSession(session);
        return getSuccessResponseVO(userInfoFromSession);
    }

    /**
     * 退出登录
     */
    @RequestMapping("logout")
    public ResponseVO logout(HttpSession session) {
        session.invalidate();
        return getSuccessResponseVO(null);
    }

    @RequestMapping("getSysSetting")
    public ResponseVO getSysSetting() {
        SysSettingDto sysSettingDto = SysCacheUtils.getSysSetting();
        SysSetting4CommentDto commentDto = sysSettingDto.getSysSetting4CommentDto();
        Map<String, Object> result = new HashMap<>();
        result.put("commnetOpen", commentDto);
        return getSuccessResponseVO(result);
    }

    @RequestMapping("resetPwd")
    public ResponseVO resetPwd(HttpSession session, @RequestBody @Valid LoginDto loginDto, String emailCode) {
        try {
            if (ObjectUtil.notEqual(loginDto.getCheckCode(), (String) session.getAttribute(Constants.CHECK_CODE_KEY))) {
                throw new BusinessException("图片验证码不正确");
            }
            userInfoService.resetPwd(loginDto, emailCode);
            return getSuccessResponseVO(null);

        } finally {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
        }

    }
}
