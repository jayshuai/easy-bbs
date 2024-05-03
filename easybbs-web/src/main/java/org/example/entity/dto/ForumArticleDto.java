package org.example.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class
ForumArticleDto {
    @NotNull(message = "板块ID不能为空")
    private String boardId;
    @NotNull(message = "板块父ID不能为空")
    @JsonProperty("pBoardId")
    private String pBoardId;

    private Integer orderType;

    private Integer pageNo = 1;

    private String currentUserId;

    private Integer status;
}
