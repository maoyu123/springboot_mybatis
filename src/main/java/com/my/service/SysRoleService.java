package com.my.service;

import com.my.entity.SysRole;
import com.my.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService {
    @Autowired(required = false)
    private SysRoleMapper sysRoleMapper;
    public SysRole selectById(Integer id){
        return sysRoleMapper.selectById(id);
    }
}
