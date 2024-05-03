package org.example.entity;

import java.io.Serializable;

/**
 * 系统设置信息(SysSetting)实体类
 *
 * @author makejava
 * @since 2024-04-04 23:51:00
 */
public class SysSetting implements Serializable {
    private static final long serialVersionUID = -73528618828332457L;
/**
     * 编号
     */
    private String code;
/**
     * 设置信息
     */
    private String jsonContent;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

}

