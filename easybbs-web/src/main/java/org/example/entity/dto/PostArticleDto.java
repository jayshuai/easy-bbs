package org.example.entity.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class PostArticleDto {
    @NotNull(message = "标题不能为空")
    @Max(value = 150, message = "标题长度不能超过150个字")
    private String title;

    private Integer boardId;

    private Integer pBoardId;

    private MultipartFile cover;

    private MultipartFile attachment;

    private String summary;

    private Integer editorType;

    private String content;

    private String markdownContent;

    private Integer integral;

}
