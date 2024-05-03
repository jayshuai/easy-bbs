package org.example;

import org.example.service.SysSettingService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InitRun implements ApplicationRunner {
    @Resource
    private SysSettingService settingService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        settingService.refreshCache();
    }
}
