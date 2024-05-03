package org.example.entity;


import lombok.Data;
import org.example.entity.enums.PageSizeEnum;

@Data
public class PageInfo {

    private Integer pageNo = 1;

    private Integer pageSize = PageSizeEnum.PAGE_SIZE_10.getSize();
}
