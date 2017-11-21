package com.ghz.flow.base.pojo;

import java.util.List;

public class Roler {
    private Integer id;

    private String rolername;

    private String keyname;


    @Override
    public String toString() {
        return "Roler{" +
                "id=" + id +
                ", rolername='" + rolername + '\'' +
                ", keyname='" + keyname + '\'' +
                ", userList=" + userList +
                '}';
    }

    //一个角色可以对应多个用户
    private List<User> userList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolername() {
        return rolername;
    }

    public void setRolername(String rolername) {
        this.rolername = rolername == null ? null : rolername.trim();
    }

    public String getKeyname() {
        return keyname;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}