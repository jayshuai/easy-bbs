package org.example.dao;

import org.example.entity.UserMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户消息(UserMessage)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-04 23:51:43
 */
public interface UserMessageDao {

    /**
     * 通过ID查询单条数据
     *
     * @param messageId 主键
     * @return 实例对象
     */
    UserMessage queryById(Integer messageId);


    /**
     * 统计总行数
     *
     * @param userMessage 查询条件
     * @return 总行数
     */
    long count(UserMessage userMessage);

    /**
     * 新增数据
     *
     * @param userMessage 实例对象
     * @return 影响行数
     */
    int insert(UserMessage userMessage);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserMessage> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserMessage> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserMessage> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserMessage> entities);

    /**
     * 修改数据
     *
     * @param userMessage 实例对象
     * @return 影响行数
     */
    int update(UserMessage userMessage);

    /**
     * 通过主键删除数据
     *
     * @param messageId 主键
     * @return 影响行数
     */
    int deleteById(Integer messageId);

    UserMessage queryByArticleIdAndCommentIdAndSendUserIdAndMessageType(@Param("articleId") String receivedUserId, Integer commentId, String sendUserId, Integer messageType);
}

