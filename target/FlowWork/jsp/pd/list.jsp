<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>审批流程列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script language="javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/pageCommon.js"
            charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/PageUtils.js" charset="utf-8"></script>
    <%--<script language="javascript" src="${pageContext.request.contextPath}/script/DemoData.js" charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/DataShowManager.js"
            charset="utf-8"></script>--%>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/pageCommon.css"/>
    <script type="text/javascript">
        function showProcessImage(processDefId) {
            var url = 'processimage.jpg'; // + processDefId;
            // myOpenWindow(url, 600, 500, "showProcessImage", true);
            myShowModelessDialog(url, 600, 500);
        }
    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13"
                 src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 审批流程管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">

        <!-- 表头-->
        <thead>
        <tr align=center valign=middle id=TableTitle>
            <td width="200px">流程名称</td>
            <td width="80px">最新版本</td>
            <td width="300px">说明</td>
            <td>相关操作</td>
        </tr>
        </thead>

        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="processDefList">
        <c:forEach items="${requestScope.list}" var="pd">
            <tr class="TableDetail1 template">
               <td>${pd.name}&nbsp;</td>
                <td align="CENTER">${pd.version}&nbsp;</td>
                <td>${pd.description}&nbsp;</td>
                <td><a onClick="return delConfirm()" href="${pageContext.request.contextPath}/processDefinitionController/delprodef.action?key=${pd.key}">删除</a>
                <a href="${pageContext.request.contextPath}/processDefinitionController/seePng.action?processDefinitionId=${pd.id}">查看流程图</a>
                  <%--  <a href="javascript:window.showModalDialog('${pageContext.request.contextPath}/seePng.action?processDefinitionId=${pd.id}','','dialogHeight:500px;dialogWidth:800px');">查看流程图</a>--%>
                </td>
            </tr>>
        </c:forEach>

        </tbody>
    </table>

    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <table border="0" cellspacing="0" cellpadding="10" align="left">
                <tr>
                    <td>
                        <div class="FuncBtn">
                            <div class=FuncBtnHead></div>
                            <div class=FuncBtnMemo><a href="${pageContext.request.contextPath}/jsp/pd/deployUI.jsp">部署流程定义文档</a></div>
                            <div class=FuncBtnTail></div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<%--<tags:pagination pag="${page}" paginationSize="${page.pageSize}"/>--%>

<div class="Description">
    说明：<br/>
    1，列表显示的是所有流程定义（不同名称）的最新版本。<br/>
    2，删除流程定义时，此名称的所有版本的流程定义都会被删除。<br/>
</div>

</body>
