package org.example.service;

import org.example.entity.UserMessage;

/**
 * 用户消息(UserMessage)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:51:43
 */
public interface UserMessageService {

    /**
     * 通过ID查询单条数据
     *
     * @param messageId 主键
     * @return 实例对象
     */
    UserMessage queryById(Integer messageId);



    /**
     * 新增数据
     *
     * @param userMessage 实例对象
     * @return 实例对象
     */
    UserMessage insert(UserMessage userMessage);

    /**
     * 修改数据
     *
     * @param userMessage 实例对象
     * @return 实例对象
     */
    UserMessage update(UserMessage userMessage);

    /**
     * 通过主键删除数据
     *
     * @param messageId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer messageId);

}
