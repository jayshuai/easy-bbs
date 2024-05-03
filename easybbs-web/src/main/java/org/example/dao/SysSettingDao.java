package org.example.dao;

import org.example.entity.SysSetting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统设置信息(SysSetting)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-04 23:51:00
 */
public interface SysSettingDao {

    /**
     * 通过ID查询单条数据
     *
     * @param code 主键
     * @return 实例对象
     */
    SysSetting queryById(String code);

    List<SysSetting> queryAll();

    /**
     * 统计总行数
     *
     * @param sysSetting 查询条件
     * @return 总行数
     */
    long count(SysSetting sysSetting);

    /**
     * 新增数据
     *
     * @param sysSetting 实例对象
     * @return 影响行数
     */
    int insert(SysSetting sysSetting);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysSetting> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysSetting> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysSetting> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SysSetting> entities);

    /**
     * 修改数据
     *
     * @param sysSetting 实例对象
     * @return 影响行数
     */
    int update(SysSetting sysSetting);

    /**
     * 通过主键删除数据
     *
     * @param code 主键
     * @return 影响行数
     */
    int deleteById(String code);

}

