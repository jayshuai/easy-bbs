package org.example.controller;

import org.example.entity.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController extends ABaseController {

    @GetMapping
    public ResponseVO<Object> test() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("name", "张三");
        return getSuccessResponseVO(testMap);
    }
}
