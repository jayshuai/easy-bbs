package org.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 文章板块信息(ForumBoard)实体类
 *
 * @author makejava
 * @since 2024-04-04 23:50:15
 */
@Data
public class ForumBoard implements Serializable {
    private static final long serialVersionUID = -31526731531990814L;
    /**
     * 板块ID
     */
    private Integer boardId;
    /**
     * 父级板块ID
     */
    private Integer pBoardId;
    /**
     * 板块名
     */
    private String boardName;
    /**
     * 封面
     */
    private String cover;
    /**
     * 描述
     */
    private String boardDesc;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 0:只允许管理员发帖 1:任何人可以发帖
     */
    private Integer postType;

    private List<ForumBoard> children;
}

