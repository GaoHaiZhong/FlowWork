package com.ghz.flow.base.pojo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private Integer id;
  @NotEmpty()
  @Length(min = 2, max = 10)
    private String loginname;

    @NotEmpty()
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", rolerList=" + rolerList +
                '}';
    }

    //一个用户可以与多个角色
    private List<Roler> rolerList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public List<Roler> getRolerList() {
        return rolerList;
    }

    public void setRolerList(List<Roler> rolerList) {
        this.rolerList = rolerList;
    }
}