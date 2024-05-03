package org.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.example.dao.SysSettingDao;
import org.example.entity.SysSetting;
import org.example.entity.dto.SysSettingDto;
import org.example.entity.enums.SysSettingCodeEnum;
import org.example.service.SysSettingService;
import org.example.utils.JsonUtils;
import org.example.utils.SysCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 系统设置信息(SysSetting)表服务实现类
 *
 * @author makejava
 * @since 2024-04-04 23:51:00
 */
@Service("sysSettingService")
public class SysSettingServiceImpl implements SysSettingService {
    private static final Logger logger = LoggerFactory.getLogger(SysSettingServiceImpl.class);
    @Resource
    private SysSettingDao sysSettingDao;

    /**
     * 通过ID查询单条数据
     *
     * @param code 主键
     * @return 实例对象
     */
    @Override
    public SysSetting queryById(String code) {
        return this.sysSettingDao.queryById(code);
    }


    /**
     * 新增数据
     *
     * @param sysSetting 实例对象
     * @return 实例对象
     */
    @Override
    public SysSetting insert(SysSetting sysSetting) {
        this.sysSettingDao.insert(sysSetting);
        return sysSetting;
    }

    /**
     * 修改数据
     *
     * @param sysSetting 实例对象
     * @return 实例对象
     */
    @Override
    public SysSetting update(SysSetting sysSetting) {
        this.sysSettingDao.update(sysSetting);
        return this.queryById(sysSetting.getCode());
    }

    /**
     * 通过主键删除数据
     *
     * @param code 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String code) {
        return this.sysSettingDao.deleteById(code) > 0;
    }

    @Override
    public void refreshCache() {
        System.out.println("服务启动了");
        try {
            SysSettingDto sysSettingDto = new SysSettingDto();
            List<SysSetting> settingList = this.sysSettingDao.queryAll();
            for (SysSetting sysSetting : settingList) {
                String jsonContent = sysSetting.getJsonContent();
                if (StrUtil.isEmpty(jsonContent)) {
                    continue;
                }
                String code = sysSetting.getCode();
                SysSettingCodeEnum sysSettingCodeEnum = SysSettingCodeEnum.getEnumByCode(code);
                PropertyDescriptor pd = new PropertyDescriptor(sysSettingCodeEnum.getProName(), SysSettingDto.class);
                Method method=pd.getWriteMethod();
                Class subClassz=Class.forName(sysSettingCodeEnum.getClazz());
                method.invoke(sysSettingDto, JsonUtils.convertJson2obj(jsonContent,subClassz));
            }
            logger.info(JSON.toJSONString(sysSettingDto));
            SysCacheUtils.refresh(sysSettingDto);
        } catch (Exception e) {
            logger.error(e.getMessage() + "刷新缓存失败");
        }
    }
}
