package com.my.mapper;

import com.my.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermissionMapper {
    List<SysPermission>listByRoleId(Integer roleId);
}
