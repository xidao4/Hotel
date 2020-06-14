package com.example.hotel.data.user;

import com.example.hotel.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface AccountMapper {

    /**
     * 创建一个新的账号
     *
     * @return
     */
     int createNewAccount(User user);

    /**
     * 根据用户名查找账号
     * @param email
     * @return
     */
     User getAccountByName(@Param("email") String email);

     User getAccountById(@Param("id") int id);

    /**
     * 更新用户信息
     * @param id
     * @param password
     * @param username
     * @param phonenumber
     * @return
     */
     int updateAccount(@Param("id") int id, @Param("password") String password,@Param("userName") String username, @Param("phoneNumber") String phonenumber, @Param("avatar_url") String avatar_url);

     int deleteUser(@Param("id") int id);

    /**
     * 根据用户输入的关键字，在所有运营人员的用户名和邮箱中查看是否存在该关键字
     * @return
     */
     List<User> searchOO(@Param("keyword") String keyword);

    List<User> searchClient(@Param("keyword") String keyword);

}
