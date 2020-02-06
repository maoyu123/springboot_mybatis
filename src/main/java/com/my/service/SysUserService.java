package com.my.service;

import com.my.entity.SysUser;
import com.my.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {
    @Autowired(required = false)
    private SysUserMapper sysUserMapper;
    public SysUser selectById(Integer id){
        return sysUserMapper.selectById(id);
    }
    public SysUser selectByName(String name){
        return sysUserMapper.selectByName(name);
    }
}
