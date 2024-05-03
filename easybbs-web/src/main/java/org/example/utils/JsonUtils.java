package org.example.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonUtils {

    public static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 队长转json
     *
     * @param object
     * @return
     */
    public static String convertObj2Json(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * json 转对象
     *
     * @param json
     * @param cla
     * @param <T>
     * @return
     */
    public static <T> T convertJson2obj(String json, Class<T> cla) {
        return JSONObject.parseObject(json, cla);
    }

    /**
     * 字符串数组转集合对象
     * @param json
     * @param classz
     * @return
     * @param <T>
     */
    public static <T> List<T> convetJsonArray2List(String json, Class<T> classz) {
        return JSONArray.parseArray(json, classz);
    }
}
