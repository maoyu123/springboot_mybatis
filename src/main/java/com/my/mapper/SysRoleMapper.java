package com.my.mapper;

import com.my.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysRoleMapper {
    @Select("select * from sys_role where id = #{id}")
    SysRole selectById(Integer id);
}
