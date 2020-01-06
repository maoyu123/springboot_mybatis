package com.my.mapper;

import com.my.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findUserByName(String name);

    public List<User> ListUser();

    public int insertUser(User user);

    public int delete(int id);

    public int Update(User user);

    public List<User> fuzzyQuery( User user);

    boolean batchImport(String fileName, MultipartFile file) throws Exception;

}
