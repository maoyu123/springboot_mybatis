package com.my.entity;

import java.io.Serializable;

public class SysRole implements Serializable {
    private static final long serialVersionUID = 152102693488134070L;
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
