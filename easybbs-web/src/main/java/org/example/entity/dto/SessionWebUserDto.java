package org.example.entity.dto;

import lombok.Data;

@Data
public class SessionWebUserDto {
    private String nickName;
    private String userId;
    private Boolean isAdmin;
    private String avatar;
    private String province;


}
