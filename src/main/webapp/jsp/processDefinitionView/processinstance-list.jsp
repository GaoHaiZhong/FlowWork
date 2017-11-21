<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.ghz.flow.*" %>
<%@ page import="com.ghz.flow.base.util.Page" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/jsp/Base/global.jsp"%>
    <%@ include file="/jsp/Base/meta.jsp" %>
    <%@ include file="/jsp/Base/include-base-styles.jsp" %>
    <script src="${ctx }/js/common/jquery.js" type="text/javascript"></script>
    <style type="text/css">
        .state-btn {float: right;}
    </style>
    <title>流程实例列表--chapter14</title>
    <script type="text/javascript">
        $(function() {
           $('.delete-btn').click(function() {
              var reason = prompt("请输入删除原因：");
               $.post(ctx + '/proInSTManager/deleteProceInst/' + $(this).data('pid'), {
                   deleteReason: reason
               }, function(resp) {
                   if (resp) {
                       location.reload();
                   } else {
                       alert('删除失败！');
                   }
               });
           });
        });
    </script>
</head>
<body>
<div class='page-title'>运行中流程实例</div>
<table width="100%" class="table table-bordered table-hover table-condensed">
    <thead>
    <tr>
        <th>流程实例ID</th>
        <th>流程定义ID</th>
        <th>流程名称</th>
        <th>流程版本</th>
        <th>业务KEY</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <!--这里运用到了分页的技术-->
    <c:forEach items="${page.result }" var="pi">
        <tr>
            <td>
                <a href="${ctx}/chapter13/process/trace/view/${pi.id}" target="_blank">${pi.id}</a>
            </td>
            <td>${pi.processDefinitionId}</td>
            <td>${definitions[pi.processDefinitionId].name}</td>
            <td>${definitions[pi.processDefinitionId].version}</td>
            <td>${pi.businessKey}</td>
            <td>
                ${pi.suspended ? '挂起' : '正常'}
                <c:if test="${pi.suspended}">
                    <a class="btn btn-small state-btn" href="${ctx }/proInSTManager/active/${pi.id}"><i class="icon-ok"></i>激活</a>
                </c:if>
                <c:if test="${!pi.suspended}">
                    <a class="btn btn-small state-btn" href="${ctx }/proInSTManager/suspend/${pi.id}"><i class="icon-lock"></i>挂起</a>
                </c:if>
            </td>
            <td>
                <a class="btn btn-small btn-danger delete-btn" data-pid="${pi.processInstanceId}"><i class="icon-remove"></i>删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--<tags:pagination pag="${page}" paginationSize="${page.pageSize}"/>--%>

<%
    Page  pages=(Page)request.getAttribute("page");

    int current = pages.getPageNo() ;
    long begin = Math.max(1, current - pages.getPageSize()/2);
    long end = Math.min(begin + (pages.getPageSize() - 1), pages.getTotalPages());
    request.setAttribute("current", current);
    request.setAttribute("begin", begin);
    request.setAttribute("end", end);
%>
<div class="pagination pagination-centered">
    <ul>
        <li class="disabled"><a>共${page.totalCount }条数据</a></li>
        <% if ((pages.isHasNext() && current != 1) || (current == end && current != 1)){%>
        <li><a href="${ctx}/proInSTManager/toProcessInstanceUI.action?p=1&ps=${page.pageSize}&${page_params}">&lt;&lt;</a></li>
        <li><a href="${ctx}/proInSTManager/toProcessInstanceUI.action?p=${current-1}&ps=${page.pageSize}&${page_params}">&lt;</a></li>
        <%}else{%>
        <li class="disabled"><a href="#">&lt;&lt;</a></li>
        <li class="disabled"><a href="#">&lt;</a></li>
        <%} %>


        <c:forEach var="i" begin="${begin}" end="${end}">
            <c:choose>
                <c:when test="${i == current}">
                    <li class="active"><a href="${ctx}/proInSTManager/toProcessInstanceUI.action?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${ctx}/proInSTManager/toProcessInstanceUI.action?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>



        <% if (pages.isHasNext()){%>
        <li><a href="${ctx}/proInSTManager/toProcessInstanceUI.action?p=${current+1}&ps=${page.pageSize}&${page_params}">&gt;</a></li>
        <li><a href="${ctx}/proInSTManager/toProcessInstanceUI.action?p=${page.totalPages}&ps=${page.pageSize}&${page_params}">&gt;&gt;</a></li>
        <%}else{%>
        <li class="disabled"><a href="#">&gt;</a></li>
        <li class="disabled"><a href="#">&gt;&gt;</a></li>
        <%} %>
    </ul>
</div>

</div>
</body>
</html>