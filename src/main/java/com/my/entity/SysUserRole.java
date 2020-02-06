package com.my.entity;

import java.io.Serializable;

public class SysUserRole implements Serializable{
    private static final long serialVersionUID = 4920165643212960736L;
    private Integer userId;
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }



}
