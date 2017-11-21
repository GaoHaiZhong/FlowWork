package com.ghz.flow.base.pojo;

import java.util.ArrayList;
import java.util.List;

public class FormTemplateExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FormTemplateExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andPdkeyIsNull() {
            addCriterion("pdkey is null");
            return (Criteria) this;
        }

        public Criteria andPdkeyIsNotNull() {
            addCriterion("pdkey is not null");
            return (Criteria) this;
        }

        public Criteria andPdkeyEqualTo(String value) {
            addCriterion("pdkey =", value, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyNotEqualTo(String value) {
            addCriterion("pdkey <>", value, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyGreaterThan(String value) {
            addCriterion("pdkey >", value, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyGreaterThanOrEqualTo(String value) {
            addCriterion("pdkey >=", value, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyLessThan(String value) {
            addCriterion("pdkey <", value, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyLessThanOrEqualTo(String value) {
            addCriterion("pdkey <=", value, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyLike(String value) {
            addCriterion("pdkey like", value, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyNotLike(String value) {
            addCriterion("pdkey not like", value, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyIn(List<String> values) {
            addCriterion("pdkey in", values, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyNotIn(List<String> values) {
            addCriterion("pdkey not in", values, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyBetween(String value1, String value2) {
            addCriterion("pdkey between", value1, value2, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPdkeyNotBetween(String value1, String value2) {
            addCriterion("pdkey not between", value1, value2, "pdkey");
            return (Criteria) this;
        }

        public Criteria andPronameIsNull() {
            addCriterion("proname is null");
            return (Criteria) this;
        }

        public Criteria andPronameIsNotNull() {
            addCriterion("proname is not null");
            return (Criteria) this;
        }

        public Criteria andPronameEqualTo(String value) {
            addCriterion("proname =", value, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameNotEqualTo(String value) {
            addCriterion("proname <>", value, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameGreaterThan(String value) {
            addCriterion("proname >", value, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameGreaterThanOrEqualTo(String value) {
            addCriterion("proname >=", value, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameLessThan(String value) {
            addCriterion("proname <", value, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameLessThanOrEqualTo(String value) {
            addCriterion("proname <=", value, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameLike(String value) {
            addCriterion("proname like", value, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameNotLike(String value) {
            addCriterion("proname not like", value, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameIn(List<String> values) {
            addCriterion("proname in", values, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameNotIn(List<String> values) {
            addCriterion("proname not in", values, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameBetween(String value1, String value2) {
            addCriterion("proname between", value1, value2, "proname");
            return (Criteria) this;
        }

        public Criteria andPronameNotBetween(String value1, String value2) {
            addCriterion("proname not between", value1, value2, "proname");
            return (Criteria) this;
        }

        public Criteria andDocpathIsNull() {
            addCriterion("docpath is null");
            return (Criteria) this;
        }

        public Criteria andDocpathIsNotNull() {
            addCriterion("docpath is not null");
            return (Criteria) this;
        }

        public Criteria andDocpathEqualTo(String value) {
            addCriterion("docpath =", value, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathNotEqualTo(String value) {
            addCriterion("docpath <>", value, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathGreaterThan(String value) {
            addCriterion("docpath >", value, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathGreaterThanOrEqualTo(String value) {
            addCriterion("docpath >=", value, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathLessThan(String value) {
            addCriterion("docpath <", value, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathLessThanOrEqualTo(String value) {
            addCriterion("docpath <=", value, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathLike(String value) {
            addCriterion("docpath like", value, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathNotLike(String value) {
            addCriterion("docpath not like", value, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathIn(List<String> values) {
            addCriterion("docpath in", values, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathNotIn(List<String> values) {
            addCriterion("docpath not in", values, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathBetween(String value1, String value2) {
            addCriterion("docpath between", value1, value2, "docpath");
            return (Criteria) this;
        }

        public Criteria andDocpathNotBetween(String value1, String value2) {
            addCriterion("docpath not between", value1, value2, "docpath");
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