package com.ghz.flow.base.pojo;

public class FormTemplate {
    private Integer id;

    private String name;

    private String pdkey;

    private String proname;

    private String docpath;

    @Override
    public String toString() {
        return "FormTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pdkey='" + pdkey + '\'' +
                ", proname='" + proname + '\'' +
                ", docpath='" + docpath + '\'' +
                '}';
    }

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
        this.name = name == null ? null : name.trim();
    }

    public String getPdkey() {
        return pdkey;
    }

    public void setPdkey(String pdkey) {
        this.pdkey = pdkey == null ? null : pdkey.trim();
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname == null ? null : proname.trim();
    }

    public String getDocpath() {
        return docpath;
    }

    public void setDocpath(String docpath) {
        this.docpath = docpath == null ? null : docpath.trim();
    }
}