package com.crispysue.mapper;
import com.crispysue.pojo.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User getUserByUsername(String username);

    /**
     * 根据 id 查询用户权限
     * @param userId
     * @return
     */
    @Select("SELECT role FROM user WHERE id = #{userId}")
    String getRoleByUserId(Integer userId);
}
