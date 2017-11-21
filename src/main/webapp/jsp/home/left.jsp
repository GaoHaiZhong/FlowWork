<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>导航菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
<script language="JavaScript" src="${pageContext.request.contextPath}/script/menu.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/menu.css" />
</head>
<body style="margin: 0">
<div id="Menu">
    <ul id="MenuUl">
        <li class="level1">
            <div onClick="menuClick(this);" class="level1Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/FUNC20057.gif" class="Icon" /> 审批流转</div>
            <ul style="display: none;" class="MenuLevel2">
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/processDefinitionController/toList.action">审批流程管理</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/fromTemplate/tolist.action">表单模板管理</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right"
                                                                                                                                             href="${pageContext.request.contextPath}/application/makeApplcation.action">起草申请</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/approve/waitApprove.action"> 待我审批</a></div>
                </li>
                <li class="level2">
                    <%--查看我的所有的申请--%>
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right"
                                                                                                                                             href="${pageContext.request.contextPath}/application/getApplicationList.action">我的申请查询</a></div>
                </li>
                <!--
		<li class="level2">
			<div class="level2Style"><img
                  src="style/images/MenuIcon/menu_arrow_single.gif" /> <a  target="right" href="Flow_FormQuery/myApprovedList.html">经我审批</a> </div>
		</li>
		-->
            </ul>
        </li>

    </ul>
</div>
<div id="Menu">
    <ul id="MenuUl">
        <li class="level1">
            <div onClick="menuClick(this);" class="level1Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/FUNC20057.gif" class="Icon" /> 作业管理</div>
            <ul style="display: none;" class="MenuLevel2">
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/processDef/toProdefManageUI.action">流程定义管理</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/proInSTManager/toProcessInstanceUI.action">流程实例管理</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right"
                                                                                                                                             href="${pageContext.request.contextPath}/jobController/toJobList.action">作业查询</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right"
                                                                                                                                             href="${pageContext.request.contextPath}/engineInfo/toEngineInfoUI.action">引擎配置参数</a></div>
                </li>
                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right"
                                                                                                                                             href="${pageContext.request.contextPath}/enginessDatabase/toDatbaseUI.action">数据库查询</a></div>
                </li>
            </ul>
        </li>

    </ul>
</div>
<div id="Menu">
    <ul id="MenuUl">
        <li class="level1">
            <div onClick="menuClick(this);" class="level1Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/FUNC20057.gif" class="Icon" />身份管理</div>
            <ul style="display: none;" class="MenuLevel2">

                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/identity/toUserList.action">身份管理界面</a></div>
                </li>

            </ul>
        </li>

    </ul>
</div>
<!--权限管理-->
<div id="Menu">
    <ul id="MenuUl">
        <li class="level1">
            <div onClick="menuClick(this);" class="level1Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/FUNC20057.gif" class="Icon" /> 权限管理</div>
            <ul style="display: none;" class="MenuLevel2">

                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/identity/toUserList.action">界面权限</a></div>
                </li>

                <li class="level2">
                    <div class="level2Style"><img src="${pageContext.request.contextPath}/style/images/MenuIcon/menu_arrow_single.gif" /> <a target="right" href="${pageContext.request.contextPath}/identity/toUserList.action">功能权限</a></div>
                </li>

            </ul>
        </li>

    </ul>
</div>


</body>
</html>
