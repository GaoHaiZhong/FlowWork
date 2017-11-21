<%@ page contentType="text/html;charset=UTF-8" language="java"  import="com.ghz.flow.base.util.Page" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>待我审批</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/PageUtils.js" charset="utf-8"></script>
   <%-- <script language="javascript" src="${pageContext.request.contextPath}/script/DemoData.js" charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/DataShowManager.js" charset="utf-8"></script>--%>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/pageCommon.css" />
    <script type="text/javascript">
    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 待我审批
        </div>
        <div id="Title_End"></div>
    </div>
</div>


<div id="QueryArea">
	<div style="height: 30px">
		<form action="#">
		<table border=0 cellspacing=3 cellpadding=5>
			<tr>
				<td>按条件查询：</td>
				<td><select name="arg1" class="SelectStyle">
						<option value="">查看全部模板</option>
						<option value="0">领款单</option>
						<option value="10">工作报告</option>
						<option value="13">设备采购单</option>
						<option value="21">员工请假单</option>
						<option value="22">加班申请</option>
					</select>
				</td>
				<td><a href=""><input type="IMAGE" src="${pageContext.request.contextPath}/style/blue/images/button/query.PNG"/></a></td>
			</tr>
		</table>

		</form>
	</div>
</div>

<form>
    <input type="hidden" name="pageNum" value="1" />
</form>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
        <tr align="CENTER" valign="MIDDLE" id="TableTitle">
            <td width="250">标题</td>
            <td width="115">申请人</td>
            <td width="115">申请日期</td>
            <td>相关操作</td>
        </tr>
        </thead>
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="formList">
        <c:forEach items="${list}" var="flowBean">
            <c:if test="${not empty flowBean.taskEntity}">
            <tr class="TableDetail1 template">
                <td><a href="approveUI.html">${flowBean.application.title}</a></td>
                <td>${flowBean.application.user.loginname}&nbsp;</td>
               <td><fmt:formatDate value="${flowBean.application.applydate}" pattern="yyyy-MM-dd HH:mm" /></td>
               <%-- 这里要根据不同流程的不同操作来判断,流程ID--%>

                <td>
                  <%--  <a href="${pageContext.request.contextPath}/${flowBean.processDefinitionEntity.key}/approve.action?id=${flowBean.taskEntity.id}">审批处理</a>--%>
                      <a href="${pageContext.request.contextPath}/${flowBean.processDefinitionEntity.key}/toTaskFormUI.action?taskId=${flowBean.taskEntity.id}">申请信息查看</a>
                      <c:if test="${not empty flowBean.taskEntity.assignee}">
                <a href="${pageContext.request.contextPath}/${flowBean.processDefinitionEntity.key}/approve.action?taskId=${flowBean.taskEntity.id}">审批处理</a>
                      </c:if>
                      <c:if test="${not empty flowBean.taskEntity.assignee}">
                          <a href="${pageContext.request.contextPath}/${flowBean.processDefinitionEntity.key}/ignsTask.action?key=${flowBean.processDefinitionEntity.key}&taskId=${flowBean.taskEntity.id}">退签</a>
                      </c:if>
                      <c:if test="${empty flowBean.taskEntity.assignee}">
                          <a href="${pageContext.request.contextPath}/${flowBean.processDefinitionEntity.key}/claimTask/${flowBean.processDefinitionEntity.key}.action?taskId=${flowBean.taskEntity.id}">签收</a>
                      </c:if>
                      <c:if test="${flowBean.taskEntity.name=='销假'}">
                           <a href="${pageContext.request.contextPath}/${flowBean.processDefinitionEntity.key}/xiaoJia/${flowBean.taskEntity.id}">销假</a>
                      </c:if>
                    <!-- <a href="showForm.html">查看申请信息</a> -->
                    <a href="${pageContext.request.contextPath}/${flowBean.processDefinitionEntity.key}/toApprovedHistoryUI.action?processInstanceId=${flowBean.taskEntity.processInstanceId}">查看流转记录</a>
                    <a href="${pageContext.request.contextPath}/${flowBean.processDefinitionEntity.key}/followPircuter.action?id=${flowBean.taskEntity.id}">图形跟踪</a>
                     <fmt:formatDate value="${flowBean.taskEntity.dueDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd hh:mm" var="endTime"/>
                        <c:if test="${not empty endTime}">
                               (已处理)
                        </c:if>
                        <c:if test="${empty endTime }">
                          (未处理)
                        </c:if>

                    <c:set var="subTask" value="${flowBean.taskEntity.parentTaskId}"></c:set>
                     <c:if test="${not empty subTask}"> （属于子流程）</c:if>
                </td>


            </tr>
            </c:if>
        </c:forEach>
       <%-- <tr class="TableDetail1 template">
            <td><a href="approveUI.html">${form.title}</a></td>
            <td>${form.applicant.name}&nbsp;</td>
            <td>${form.applyTime}&nbsp;</td>
            <td><a href="approveUI.html">审批处理</a>
                <!-- <a href="showForm.html">查看申请信息</a> -->
                <a href="approvedHistory.html">查看流转记录</a>
            </td>
        </tr>--%>
        </tbody>
    </table>

  <%--  <%
        Page  pages=(Page)request.getAttribute("page");
                 /*   Page pag=new Page();*/
        int current = pages.getPageNo() ;
        long begin = Math.max(1, current - pages.getPageSize()/2);
        long end = Math.min(begin + (pages.getPageSize() - 1), pages.getTotalPages());
        request.setAttribute("current", current);
        request.setAttribute("begin", begin);
        request.setAttribute("end", end);
    %>--%>
    <%--<div class="pagination pagination-centered" >
        <ul>
            <li class="disabled"><a>共${page.totalCount }条数据</a></li>

            <% if ((pages.isHasNext() && current != 1) || (current == end && current != 1)){%>
            <li><a href="${ctx}/identity/toUserList.action?p=1&ps=${page.pageSize}&${page_params}">&lt;&lt;</a></li>
            <li><a href="${ctx}/identity/toUserList.action?p=${current-1}&ps=${page.pageSize}&${page_params}">&lt;</a></li>
            <%}else{%>
            <li class="disabled"><a href="#">&lt;&lt;</a></li>
            <li class="disabled"><a href="#">&lt;</a></li>
            <%} %>


            <c:forEach var="i" begin="${begin}" end="${end}">
                <c:choose>
                    <c:when test="${i == current}">
                        <li class="active"><a href="${ctx}/identity/toUserList.action?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${ctx}/identity/toUserList.action?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <% if (pages.isHasNext()){%>
            <li><a href="${ctx}/identity/toUserList.action?p=${current+1}&ps=${page.pageSize}&${page_params}">&gt;</a></li>
            <li><a href="${ctx}/identity/toUserList.action?p=${page.totalPages}&ps=${page.pageSize}&${page_params}">&gt;&gt;</a></li>
            <%}else{%>
            <li class="disabled"><a href="#">&gt;</a></li>
            <li class="disabled"><a href="#">&gt;&gt;</a></li>
            <%} %>
        </ul>
    </div>--%>

    <!-- 其他功能超链接 -->
    <div id="TableTail"><div id="TableTail_inside"></div></div>
</div>

   <%-- 审批记录--%>
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 审批记录
        </div>
        <div id="Title_End"></div>
    </div>
</div>
        <div id="MainArea">
            <table cellspacing="0" cellpadding="0" class="TableStyle">
                <!-- 表头-->
                <thead>
                <tr align="CENTER" valign="MIDDLE" id="TableTitle">
                    <td width="250">标题</td>
                    <td width="115">申请人</td>
                    <td width="115">申请日期</td>
                    <td>相关操作</td>
                </tr>
                </thead>
                <!--显示数据列表-->
                <tbody id="TableData" class="dataContainer" datakey="formList">
                <c:forEach items="${hislist}" var="flowBean">
                    <c:if test="${not empty flowBean.historicTaskInstanceEntity}">
                        <tr class="TableDetail1 template">
                            <td><a href="approveUI.html">${flowBean.application.title}</a></td>
                            <td>${flowBean.application.user.loginname}&nbsp;</td>
                            <td><fmt:formatDate value="${flowBean.application.applydate}" pattern="yyyy-MM-dd HH:mm"/></td>
                                <%--   <td>${application.applydate}&nbsp;</td>--%>
                            <td><a href="${pageContext.request.contextPath}/${flowBean.processDefinitionEntity.key}/toApprovedHistoryUI.action?processInstanceId=${flowBean.historicTaskInstanceEntity.processInstanceId}">查看审批信息</a>
                                <%--判断是否处理--%>
                                <fmt:formatDate value="${flowBean.historicTaskInstanceEntity.endTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd hh:mm" var="hisEndTime"/>

                                <c:if test="${not empty hisEndTime}">
                                    (已处理)
                                </c:if>
                                <c:if test="${empty hisEndTime}">
                                    (未处理)
                                </c:if>
                                <!--判断是否子流程-->
                                <c:set var="subTask" value="${flowBean.historicTaskInstanceEntity.parentTaskId}"></c:set>
                                <c:if test="${not empty subTask}"> （属于子流程）</c:if>

                                <%--判断流程是否结束--%>
                                <fmt:formatDate value="${flowBean.historicProcessInstanceEntity.endTime}" type="both" dateStyle="long" pattern="yyyy-MM-dd hh:mm" var="hisProEndTime"/>
                                <c:if test="${not empty hisProEndTime}">
                                    (流程已结束)
                                </c:if>
                                <c:if test="${empty hisProEndTime}">
                                    (流程未结束)
                                </c:if>

                            </td>
                        </tr>
                    </c:if>
                </c:forEach>

                </tbody>
            </table>
          <%--  <%
                Page  pages=(Page)request.getAttribute("page");
                 /*   Page pag=new Page();*/
                int current = pages.getPageNo() ;
                long begin = Math.max(1, current - pages.getPageSize()/2);
                long end = Math.min(begin + (pages.getPageSize() - 1), pages.getTotalPages());
                request.setAttribute("current", current);
                request.setAttribute("begin", begin);
                request.setAttribute("end", end);
            %>--%>
           <%-- <div class="pagination pagination-centered" >
                <ul>
                    <li class="disabled"><a>共${page.totalCount }条数据</a></li>

                    <% if ((pages.isHasNext() && current != 1) || (current == end && current != 1)){%>
                    <li><a href="${ctx}/identity/toUserList.action?p=1&ps=${page.pageSize}&${page_params}">&lt;&lt;</a></li>
                    <li><a href="${ctx}/identity/toUserList.action?p=${current-1}&ps=${page.pageSize}&${page_params}">&lt;</a></li>
                    <%}else{%>
                    <li class="disabled"><a href="#">&lt;&lt;</a></li>
                    <li class="disabled"><a href="#">&lt;</a></li>
                    <%} %>


                    <c:forEach var="i" begin="${begin}" end="${end}">
                        <c:choose>
                            <c:when test="${i == current}">
                                <li class="active"><a href="${ctx}/identity/toUserList.action?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="${ctx}/identity/toUserList.action?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <% if (pages.isHasNext()){%>
                    <li><a href="${ctx}/identity/toUserList.action?p=${current+1}&ps=${page.pageSize}&${page_params}">&gt;</a></li>
                    <li><a href="${ctx}/identity/toUserList.action?p=${page.totalPages}&ps=${page.pageSize}&${page_params}">&gt;&gt;</a></li>
                    <%}else{%>
                    <li class="disabled"><a href="#">&gt;</a></li>
                    <li class="disabled"><a href="#">&gt;&gt;</a></li>
                    <%} %>
                </ul>
            </div>--%>

            <!-- 其他功能超链接 -->
            <div id="TableTail"><div id="TableTail_inside"></div></div>
        </div>


    </div>
</div>

</body>
</html>

