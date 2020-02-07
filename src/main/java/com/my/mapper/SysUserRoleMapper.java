package com.my.mapper;

import com.my.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    @Select("select user_id userId,role_id roleId from sys_user_role where user_id = #{userId}")
    List<SysUserRole>listByUserId(Integer userId);

}
