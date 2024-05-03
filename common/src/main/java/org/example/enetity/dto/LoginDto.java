package org.example.enetity.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LoginDto {

    @NotNull(message = "邮箱不能为空")
    @Email
    private String email;

    @NotNull(message = "密码不能为空")
    @Length(min = 6,max = 15 ,message = "密码至少6至16位")
    private String password;

    @NotNull(message = "验证码不能为空")
    private String checkCode;
}
