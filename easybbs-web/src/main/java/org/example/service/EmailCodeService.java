package org.example.service;

import org.example.entity.EmailCode;


/**
 * 邮箱验证码(EmailCode)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:33:40
 */
public interface EmailCodeService {

    /**
     * 通过ID查询单条数据
     *
     * @param email 主键
     * @return 实例对象
     */
    EmailCode queryById(String email);


    /**
     * 新增数据
     *
     * @param emailCode 实例对象
     * @return 实例对象
     */
    EmailCode insert(EmailCode emailCode);

    /**
     * 修改数据
     *
     * @param emailCode 实例对象
     * @return 实例对象
     */
    EmailCode update(EmailCode emailCode);

    /**
     * 通过主键删除数据
     *
     * @param email 主键
     * @return 是否成功
     */
    boolean deleteById(String email);

    void sendEmailCode(String email, Integer type);

    void checkCode(String email,String checkCode);

}
