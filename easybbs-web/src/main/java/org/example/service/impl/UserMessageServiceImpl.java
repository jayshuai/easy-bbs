package org.example.service.impl;

import org.example.dao.UserMessageDao;
import org.example.entity.UserMessage;
import org.example.service.UserMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户消息(UserMessage)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:51:43
 */
@Service("userMessageService")
public class UserMessageServiceImpl implements UserMessageService {
    @Resource
    private UserMessageDao userMessageDao;

    /**
     * 通过ID查询单条数据
     *
     * @param messageId 主键
     * @return 实例对象
     */
    @Override
    public UserMessage queryById(Integer messageId) {
        return this.userMessageDao.queryById(messageId);
    }


    /**
     * 新增数据
     *
     * @param userMessage 实例对象
     * @return 实例对象
     */
    @Override
    public UserMessage insert(UserMessage userMessage) {
        this.userMessageDao.insert(userMessage);
        return userMessage;
    }

    /**
     * 修改数据
     *
     * @param userMessage 实例对象
     * @return 实例对象
     */
    @Override
    public UserMessage update(UserMessage userMessage) {
        this.userMessageDao.update(userMessage);
        return this.queryById(userMessage.getMessageId());
    }

    /**
     * 通过主键删除数据
     *
     * @param messageId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer messageId) {
        return this.userMessageDao.deleteById(messageId) > 0;
    }
}
