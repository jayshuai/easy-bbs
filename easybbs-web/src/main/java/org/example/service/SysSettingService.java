package org.example.service;

import org.example.entity.SysSetting;

/**
 * 系统设置信息(SysSetting)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:51:00
 */
public interface SysSettingService {

    /**
     * 通过ID查询单条数据
     *
     * @param code 主键
     * @return 实例对象
     */
    SysSetting queryById(String code);



    /**
     * 新增数据
     *
     * @param sysSetting 实例对象
     * @return 实例对象
     */
    SysSetting insert(SysSetting sysSetting);

    /**
     * 修改数据
     *
     * @param sysSetting 实例对象
     * @return 实例对象
     */
    SysSetting update(SysSetting sysSetting);

    /**
     * 通过主键删除数据
     *
     * @param code 主键
     * @return 是否成功
     */
    boolean deleteById(String code);

    void  refreshCache();

}
