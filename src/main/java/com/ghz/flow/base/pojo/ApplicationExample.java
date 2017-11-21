package com.ghz.flow.base.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplicationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ApplicationExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andApplicantIsNull() {
            addCriterion("applicant is null");
            return (Criteria) this;
        }

        public Criteria andApplicantIsNotNull() {
            addCriterion("applicant is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantEqualTo(Integer value) {
            addCriterion("applicant =", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotEqualTo(Integer value) {
            addCriterion("applicant <>", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantGreaterThan(Integer value) {
            addCriterion("applicant >", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantGreaterThanOrEqualTo(Integer value) {
            addCriterion("applicant >=", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLessThan(Integer value) {
            addCriterion("applicant <", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLessThanOrEqualTo(Integer value) {
            addCriterion("applicant <=", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantIn(List<Integer> values) {
            addCriterion("applicant in", values, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotIn(List<Integer> values) {
            addCriterion("applicant not in", values, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantBetween(Integer value1, Integer value2) {
            addCriterion("applicant between", value1, value2, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotBetween(Integer value1, Integer value2) {
            addCriterion("applicant not between", value1, value2, "applicant");
            return (Criteria) this;
        }

        public Criteria andDocfilepathIsNull() {
            addCriterion("docFilePath is null");
            return (Criteria) this;
        }

        public Criteria andDocfilepathIsNotNull() {
            addCriterion("docFilePath is not null");
            return (Criteria) this;
        }

        public Criteria andDocfilepathEqualTo(String value) {
            addCriterion("docFilePath =", value, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathNotEqualTo(String value) {
            addCriterion("docFilePath <>", value, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathGreaterThan(String value) {
            addCriterion("docFilePath >", value, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathGreaterThanOrEqualTo(String value) {
            addCriterion("docFilePath >=", value, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathLessThan(String value) {
            addCriterion("docFilePath <", value, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathLessThanOrEqualTo(String value) {
            addCriterion("docFilePath <=", value, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathLike(String value) {
            addCriterion("docFilePath like", value, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathNotLike(String value) {
            addCriterion("docFilePath not like", value, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathIn(List<String> values) {
            addCriterion("docFilePath in", values, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathNotIn(List<String> values) {
            addCriterion("docFilePath not in", values, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathBetween(String value1, String value2) {
            addCriterion("docFilePath between", value1, value2, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andDocfilepathNotBetween(String value1, String value2) {
            addCriterion("docFilePath not between", value1, value2, "docfilepath");
            return (Criteria) this;
        }

        public Criteria andApplydateIsNull() {
            addCriterion("applyDate is null");
            return (Criteria) this;
        }

        public Criteria andApplydateIsNotNull() {
            addCriterion("applyDate is not null");
            return (Criteria) this;
        }

        public Criteria andApplydateEqualTo(Date value) {
            addCriterion("applyDate =", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotEqualTo(Date value) {
            addCriterion("applyDate <>", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateGreaterThan(Date value) {
            addCriterion("applyDate >", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateGreaterThanOrEqualTo(Date value) {
            addCriterion("applyDate >=", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateLessThan(Date value) {
            addCriterion("applyDate <", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateLessThanOrEqualTo(Date value) {
            addCriterion("applyDate <=", value, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateIn(List<Date> values) {
            addCriterion("applyDate in", values, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotIn(List<Date> values) {
            addCriterion("applyDate not in", values, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateBetween(Date value1, Date value2) {
            addCriterion("applyDate between", value1, value2, "applydate");
            return (Criteria) this;
        }

        public Criteria andApplydateNotBetween(Date value1, Date value2) {
            addCriterion("applyDate not between", value1, value2, "applydate");
            return (Criteria) this;
        }

        public Criteria andRejecttimeIsNull() {
            addCriterion("rejecttime is null");
            return (Criteria) this;
        }

        public Criteria andRejecttimeIsNotNull() {
            addCriterion("rejecttime is not null");
            return (Criteria) this;
        }

        public Criteria andRejecttimeEqualTo(Integer value) {
            addCriterion("rejecttime =", value, "rejecttime");
            return (Criteria) this;
        }

        public Criteria andRejecttimeNotEqualTo(Integer value) {
            addCriterion("rejecttime <>", value, "rejecttime");
            return (Criteria) this;
        }

        public Criteria andRejecttimeGreaterThan(Integer value) {
            addCriterion("rejecttime >", value, "rejecttime");
            return (Criteria) this;
        }

        public Criteria andRejecttimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("rejecttime >=", value, "rejecttime");
            return (Criteria) this;
        }

        public Criteria andRejecttimeLessThan(Integer value) {
            addCriterion("rejecttime <", value, "rejecttime");
            return (Criteria) this;
        }

        public Criteria andRejecttimeLessThanOrEqualTo(Integer value) {
            addCriterion("rejecttime <=", value, "rejecttime");
            return (Criteria) this;
        }

        public Criteria andRejecttimeIn(List<Integer> values) {
            addCriterion("rejecttime in", values, "rejecttime");
            return (Criteria) this;
        }

        public Criteria andRejecttimeNotIn(List<Integer> values) {
            addCriterion("rejecttime not in", values, "rejecttime");
            return (Criteria) this;
        }

        public Criteria andRejecttimeBetween(Integer value1, Integer value2) {
            addCriterion("rejecttime between", value1, value2, "rejecttime");
            return (Criteria) this;
        }

        public Criteria andRejecttimeNotBetween(Integer value1, Integer value2) {
            addCriterion("rejecttime not between", value1, value2, "rejecttime");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeIsNull() {
            addCriterion("againapplytime is null");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeIsNotNull() {
            addCriterion("againapplytime is not null");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeEqualTo(Integer value) {
            addCriterion("againapplytime =", value, "againapplytime");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeNotEqualTo(Integer value) {
            addCriterion("againapplytime <>", value, "againapplytime");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeGreaterThan(Integer value) {
            addCriterion("againapplytime >", value, "againapplytime");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("againapplytime >=", value, "againapplytime");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeLessThan(Integer value) {
            addCriterion("againapplytime <", value, "againapplytime");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeLessThanOrEqualTo(Integer value) {
            addCriterion("againapplytime <=", value, "againapplytime");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeIn(List<Integer> values) {
            addCriterion("againapplytime in", values, "againapplytime");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeNotIn(List<Integer> values) {
            addCriterion("againapplytime not in", values, "againapplytime");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeBetween(Integer value1, Integer value2) {
            addCriterion("againapplytime between", value1, value2, "againapplytime");
            return (Criteria) this;
        }

        public Criteria andAgainapplytimeNotBetween(Integer value1, Integer value2) {
            addCriterion("againapplytime not between", value1, value2, "againapplytime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andTemplateIsNull() {
            addCriterion("template is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIsNotNull() {
            addCriterion("template is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateEqualTo(Integer value) {
            addCriterion("template =", value, "template");
            return (Criteria) this;
        }

        public Criteria andTemplateNotEqualTo(Integer value) {
            addCriterion("template <>", value, "template");
            return (Criteria) this;
        }

        public Criteria andTemplateGreaterThan(Integer value) {
            addCriterion("template >", value, "template");
            return (Criteria) this;
        }

        public Criteria andTemplateGreaterThanOrEqualTo(Integer value) {
            addCriterion("template >=", value, "template");
            return (Criteria) this;
        }

        public Criteria andTemplateLessThan(Integer value) {
            addCriterion("template <", value, "template");
            return (Criteria) this;
        }

        public Criteria andTemplateLessThanOrEqualTo(Integer value) {
            addCriterion("template <=", value, "template");
            return (Criteria) this;
        }

        public Criteria andTemplateIn(List<Integer> values) {
            addCriterion("template in", values, "template");
            return (Criteria) this;
        }

        public Criteria andTemplateNotIn(List<Integer> values) {
            addCriterion("template not in", values, "template");
            return (Criteria) this;
        }

        public Criteria andTemplateBetween(Integer value1, Integer value2) {
            addCriterion("template between", value1, value2, "template");
            return (Criteria) this;
        }

        public Criteria andTemplateNotBetween(Integer value1, Integer value2) {
            addCriterion("template not between", value1, value2, "template");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}