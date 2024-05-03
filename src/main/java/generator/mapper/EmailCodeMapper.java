package generator.mapper;

import generator.dao.EmailCode;

/**
* @author Administrator
* @description 针对表【email_code(邮箱验证码)】的数据库操作Mapper
* @createDate 2024-04-05 19:18:40
* @Entity generator.dao.EmailCode
*/
public interface EmailCodeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(EmailCode record);

    int insertSelective(EmailCode record);

    EmailCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EmailCode record);

    int updateByPrimaryKey(EmailCode record);

}
