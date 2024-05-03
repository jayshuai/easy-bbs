package org.example.service;

import org.example.enetity.dto.LoginDto;
import org.example.entity.UserInfo;
import org.example.entity.dto.SessionWebUserDto;
import org.example.entity.enums.UserIntegralOperTypeEnum;

/**
 * 用户信息(UserInfo)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:51:14
 */
public interface UserInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserInfo queryById(String userId);


    /**
     * 新增数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    UserInfo insert(UserInfo userInfo);

    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    UserInfo update(UserInfo userInfo);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(String userId);

    void register(String email, String nickName, String emailCode, String password);

    void updateUserIntegral(String userId, UserIntegralOperTypeEnum userIntegralOperTypeEnum, Integer changeType, Integer integral);

    SessionWebUserDto login(String email, String password, String ip);

    void resetPwd(LoginDto loginDto, String emailCode);
}
