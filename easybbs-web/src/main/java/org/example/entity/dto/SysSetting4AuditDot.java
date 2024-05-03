package org.example.entity.dto;

import lombok.Data;

/**
 * 审核设置
 */
@Data
public class SysSetting4AuditDot {
    /**
     * 帖子是否需要审核
     */
    private Boolean postAudit;

    private Boolean commentAudit;
}
