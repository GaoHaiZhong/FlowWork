package com.ghz.flow.base.pojo;

import java.util.Date;

public class Application {
    private Integer id;

    private String title;

    private Integer applicant;

    private String docfilepath;

    private Date applydate;

    private Integer rejecttime;

    private Integer againapplytime;

    private String status;

    private Integer template;

    /**
     * 三个状态
     */
    public static final String STATUS_APPROVING = "审批中";
    public static final String STATUS_UNAPPROVED = "未通过";
    public static final String STATUS_APPROVED = "已通过";

    private User user;
    private FormTemplate formTemplate;

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", applicant=" + applicant +
                ", docfilepath='" + docfilepath + '\'' +
                ", applydate=" + applydate +
                ", rejecttime=" + rejecttime +
                ", againapplytime=" + againapplytime +
                ", status='" + status + '\'' +
                ", template=" + template +
                ", user=" + user +
                ", formTemplate=" + formTemplate +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FormTemplate getFormTemplate() {
        return formTemplate;
    }

    public void setFormTemplate(FormTemplate formTemplate) {
        this.formTemplate = formTemplate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getApplicant() {
        return applicant;
    }

    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
    }

    public String getDocfilepath() {
        return docfilepath;
    }

    public void setDocfilepath(String docfilepath) {
        this.docfilepath = docfilepath == null ? null : docfilepath.trim();
    }

    public Date getApplydate() {
        return applydate;
    }

    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    public Integer getRejecttime() {
        return rejecttime;
    }

    public void setRejecttime(Integer rejecttime) {
        this.rejecttime = rejecttime;
    }

    public Integer getAgainapplytime() {
        return againapplytime;
    }

    public void setAgainapplytime(Integer againapplytime) {
        this.againapplytime = againapplytime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getTemplate() {
        return template;
    }

    public void setTemplate(Integer template) {
        this.template = template;
    }

}