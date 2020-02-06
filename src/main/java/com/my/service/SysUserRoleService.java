package com.my.service;

import com.my.entity.SysUserRole;
import com.my.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleService {

    @Autowired(required = false)
    private SysUserRoleMapper sysUserRoleMapper;

    public List<SysUserRole> listByUserId(Integer userId){
        return sysUserRoleMapper.listByUserId(userId);
    }
}
