package org.example.service.impl;

import org.example.dao.UserIntegralRecordDao;
import org.example.entity.UserIntegralRecord;
import org.example.service.UserIntegralRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户积分记录表(UserIntegralRecord)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:51:31
 */
@Service("userIntegralRecordService")
public class UserIntegralRecordServiceImpl implements UserIntegralRecordService {
    @Resource
    private UserIntegralRecordDao userIntegralRecordDao;

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    @Override
    public UserIntegralRecord queryById(Integer recordId) {
        return this.userIntegralRecordDao.queryById(recordId);
    }



    /**
     * 新增数据
     *
     * @param userIntegralRecord 实例对象
     * @return 实例对象
     */
    @Override
    public UserIntegralRecord insert(UserIntegralRecord userIntegralRecord) {
        this.userIntegralRecordDao.insert(userIntegralRecord);
        return userIntegralRecord;
    }

    /**
     * 修改数据
     *
     * @param userIntegralRecord 实例对象
     * @return 实例对象
     */
    @Override
    public UserIntegralRecord update(UserIntegralRecord userIntegralRecord) {
        this.userIntegralRecordDao.update(userIntegralRecord);
        return this.queryById(userIntegralRecord.getRecordId());
    }

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer recordId) {
        return this.userIntegralRecordDao.deleteById(recordId) > 0;
    }
}
