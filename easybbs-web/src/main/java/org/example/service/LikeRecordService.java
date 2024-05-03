package org.example.service;

import org.example.entity.LikeRecord;
import org.example.entity.enums.OperRecordOpTypeEnum;

/**
 * 点赞记录(LikeRecord)表服务接口
 *
 * @author makejava
 * @since 2024-04-04 23:50:46
 */
public interface LikeRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param opId 主键
     * @return 实例对象
     */
    LikeRecord queryById(Integer opId);


    /**
     * 新增数据
     *
     * @param likeRecord 实例对象
     * @return 实例对象
     */
    LikeRecord insert(LikeRecord likeRecord);

    /**
     * 修改数据
     *
     * @param likeRecord 实例对象
     * @return 实例对象
     */
    LikeRecord update(LikeRecord likeRecord);

    /**
     * 通过主键删除数据
     *
     * @param opId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer opId);

    LikeRecord getRecordByObjectIdAndUserIdAndOrderType(String objectId, String userId, Integer type);

    void doLike(String objectId, String userId, String nickName, OperRecordOpTypeEnum opTypeEnum);
}
