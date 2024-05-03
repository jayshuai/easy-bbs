package org.example.dao;

import org.apache.ibatis.annotations.Param;
import org.example.entity.EmailCode;

import java.util.List;

/**
 * 邮箱验证码(EmailCode)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-04 23:36:09
 */
public interface EmailCodeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param email 主键
     * @return 实例对象
     */
    EmailCode queryById(String email);

    /**
     * 统计总行数
     *
     * @param emailCode 查询条件
     * @return 总行数
     */
    long count(EmailCode emailCode);

    /**
     * 新增数据
     *
     * @param emailCode 实例对象
     * @return 影响行数
     */
    int insert(EmailCode emailCode);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<EmailCode> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<EmailCode> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<EmailCode> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<EmailCode> entities);

    /**
     * 修改数据
     *
     * @param emailCode 实例对象
     * @return 影响行数
     */
    int update(EmailCode emailCode);

    /**
     * 通过主键删除数据
     *
     * @param email 主键
     * @return 影响行数
     */
    int deleteById(String email);

    void disableEmailCode(@Param("email") String email);

    EmailCode queryByEmailAndCode(@Param("email") String email, @Param("code") String checkCode);
}

