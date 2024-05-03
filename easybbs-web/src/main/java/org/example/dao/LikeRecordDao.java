package org.example.dao;

import org.apache.ibatis.annotations.Param;
import org.example.entity.LikeRecord;

import java.util.List;

/**
 * 点赞记录(LikeRecord)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-04 23:50:46
 */
public interface LikeRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param opId 主键
     * @return 实例对象
     */
    LikeRecord queryById(Integer opId);


    /**
     * 统计总行数
     *
     * @param likeRecord 查询条件
     * @return 总行数
     */
    long count(LikeRecord likeRecord);

    /**
     * 新增数据
     *
     * @param likeRecord 实例对象
     * @return 影响行数
     */
    int insert(LikeRecord likeRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<LikeRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<LikeRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<LikeRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<LikeRecord> entities);

    /**
     * 修改数据
     *
     * @param likeRecord 实例对象
     * @return 影响行数
     */
    int update(LikeRecord likeRecord);

    /**
     * 通过主键删除数据
     *
     * @param opId 主键
     * @return 影响行数
     */
    int deleteById(Integer opId);

    LikeRecord queryRecordByObjectIdAndUserIdAndOrderType(String objectId, String userId, @Param("opType") Integer type);

    void deleteByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer type);
}

