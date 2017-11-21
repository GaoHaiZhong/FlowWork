<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>流转记录</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/pageCommon.css" />
    <script type="text/javascript">
    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 审批流转记录
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">

    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <tr align="CENTER" valign="MIDDLE" id="TableTitle">
            <td width="115">审批人</td>
            <td width="115">审批时间</td>
            <td width="100">是否通过</td>
            <td>审批意见</td>
        </tr>

        <tbody id="TableData" class="dataContainer" datakey="approveInfoList">
        <!--显示数据列表-->
        <c:forEach items="${commentList}" var="flowBean" varStatus="status">


        <tr class="TableDetail1 template">
            <td>${flowBean.historicActivityInstanceEntity.assignee}&nbsp;</td>
            <td>
                <fmt:formatDate value="${flowBean.historicActivityInstanceEntity.endTime}" pattern="yyyy-MM-dd HH:mm" />&nbsp;
            </td>

            <td>${flowBean.historicActivityInstanceEntity.activityName}&nbsp;</td>

                <c:forEach items="${flowBean.commentList}"  var="com">
                   <%-- <c:choose>
                        <c:when test="${com.fullMessage}!=null">
                            <td>${com.fullMessage}&nbsp;</td>
                        </c:when>
                        <c:otherwise>
                            <td>&nbsp;</td>
                        </c:otherwise>
                    </c:choose>--%>
                <%--   <c:if test="${com.fullMessage}!=null">--%>
                        <td>${com.fullMessage}&nbsp;</td>
                  <%--  </c:if>--%>
                </c:forEach>
        </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <a href="${pageContext.request.contextPath}/approve/waitApprove.action"><img src="${pageContext.request.contextPath}/style/images/goBack.png"/></a>
        </div>
    </div>
</div>

<div class="Description">
    说明：<br />
    1，审批信息列表按审批时间升序排列。<br />
</div>

</body>
</html>