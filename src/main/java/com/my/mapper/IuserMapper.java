package com.my.mapper;

import com.my.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IuserMapper {

    List<User> ListUser();
    List<User> findUserByName(String name);
    public int Update(User user);
    public int insertUser(User user);
}
