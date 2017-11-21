<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>表单模板列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/PageUtils.js" charset="utf-8"></script>
   <%-- <script language="javascript" src="../script/DemoData.js" charset="utf-8"></script>
    <script language="javascript" src="../script/DataShowManager.js" charset="utf-8"></script>--%>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/pageCommon.css" />
    <script type="text/javascript">


    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 模板管理
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">

        <!-- 表头-->
        <thead>
        <tr align=center valign=middle id=TableTitle>
            <td width="250px">模板名称</td>
            <td width="250px">所用流程</td>
            <td>相关操作</td>
        </tr>
        </thead>

        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="documentTemplateList">
        <c:forEach items="${list}" var="template">
            <tr class="TableDetail1 template">
                <td><a href="javascript:void('下载')">${template.name}</a>&nbsp;</td>
                <!--所有流程-->

                 <%--<!--所有流程-->--%>
                <%--<td>
                <c:forEach items="${namekey}" var="pd">
                    <td>${pd.name}&nbsp;</td>
                </c:forEach>
                </td>--%>
               <td id="pd">${template.proname}&nbsp;</td>
                <td><a onClick="return delConfirm()" href="${pageContext.request.contextPath}/fromTemplate/deleteById.action?id=${template.id}">删除</a>
                    <a href="${pageContext.request.contextPath}/fromTemplate/toUpdateSaveUIBy.action?id=${template.id}">修改</a>
                    <a href="${pageContext.request.contextPath}/fromTemplate/upload.action?id=${template.id}">下载</a>
                </td>
            </tr>
        </c:forEach>
       <%-- <tr class="TableDetail1 template">
            <td><a href="javascript:void('下载')">${documentTemplate.name}</a>&nbsp;</td>
            <td>${documentTemplate.processName}&nbsp;</td>
            <td><a onClick="return delConfirm()" href="#">删除</a>
                <a href="saveUI.html">修改</a>
                <a href="#">下载</a>
            </td>
        </tr>--%>
        </tbody>
    </table>

    <!-- 其他功能超链接 -->
    <div id="TableTail">
        <div id="TableTail_inside">
            <a href="${pageContext.request.contextPath}/fromTemplate/toSaveUI.action"><img src="${pageContext.request.contextPath}/style/blue/images/button/addNew.PNG" /></a>
        </div>
    </div>

    <div class="description">
        说明：<br />
        1，删除时，相应的文件也被删除。<br />
        2，下载时，文件名默认为：{表单模板名称}.doc<br />
    </div>

</div>
</body>
</html>

