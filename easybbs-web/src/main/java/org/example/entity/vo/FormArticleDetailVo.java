package org.example.entity.vo;

import lombok.Data;

@Data
public class FormArticleDetailVo {
    private ForumArticleVo forumArticleVo;

    private ForumArticleAttachmentVo attachmentVo;

    private Boolean haveLike = false;
}
