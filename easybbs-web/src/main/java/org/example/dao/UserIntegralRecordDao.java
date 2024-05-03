package org.example.dao;

import org.example.entity.UserIntegralRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户积分记录表(UserIntegralRecord)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-04 23:51:31
 */
public interface UserIntegralRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    UserIntegralRecord queryById(Integer recordId);


    /**
     * 统计总行数
     *
     * @param userIntegralRecord 查询条件
     * @return 总行数
     */
    long count(UserIntegralRecord userIntegralRecord);

    /**
     * 新增数据
     *
     * @param userIntegralRecord 实例对象
     * @return 影响行数
     */
    int insert(UserIntegralRecord userIntegralRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserIntegralRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserIntegralRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserIntegralRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserIntegralRecord> entities);

    /**
     * 修改数据
     *
     * @param userIntegralRecord 实例对象
     * @return 影响行数
     */
    int update(UserIntegralRecord userIntegralRecord);

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 影响行数
     */
    int deleteById(Integer recordId);

}

