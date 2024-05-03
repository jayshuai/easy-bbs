package org.example.service;

import org.example.entity.UserIntegralRecord;

/**
 * 用户积分记录表(UserIntegralRecord)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:51:31
 */
public interface UserIntegralRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    UserIntegralRecord queryById(Integer recordId);



    /**
     * 新增数据
     *
     * @param userIntegralRecord 实例对象
     * @return 实例对象
     */
    UserIntegralRecord insert(UserIntegralRecord userIntegralRecord);

    /**
     * 修改数据
     *
     * @param userIntegralRecord 实例对象
     * @return 实例对象
     */
    UserIntegralRecord update(UserIntegralRecord userIntegralRecord);

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer recordId);

}
