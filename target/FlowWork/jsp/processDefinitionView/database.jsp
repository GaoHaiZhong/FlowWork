<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.ghz.flow.base.util.Page" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/jsp/Base/global.jsp"%>
    <%@ include file="/jsp/Base/meta.jsp" %>
    <%@ include file="/jsp/Base/include-base-styles.jsp" %>
    <script src="${ctx }/js/common/jquery.js" type="text/javascript"></script>
</head>
<body>
<div class='page-title ui-corner-all'>引擎数据库</div>
    <div class="row">
        <div class="span4" style="border-right: 1px solid #000000">
            <div class='page-title ui-corner-all'>表名</div>
            <ul class="unstyled">
                <c:forEach items="${tableCount}" var="table">
                    <li><a href="${ctx}/enginessDatabase/toDatbaseUI.action?tableName=${table.key}">${table.key}（${table.value}）</a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="span8">
            <c:if test="${not empty tableMetaData}">
            <div class='page-title ui-corner-all'>记录（${tableMetaData.tableName}）</div>
            <table width="100%" class="table table-bordered table-hover table-condensed">
                <thead>
                    <tr>
                        <th>行</th>
                        <c:forEach items="${tableMetaData.columnNames}" var="data" varStatus="row">
                            <th title="字段类型：${tableMetaData.columnTypes[row.index]}">${data}</th>
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="counter" value="${((page.pageNo - 1) * page.pageSize) + 1}" />
                    <c:forEach items="${page.result}" var="data">
                    <tr>
                        <td style="padding: 0 10px 0 10px">${counter}</td>
                        <c:set var="counter" value="${counter+1}" />
                        <c:forEach items="${tableMetaData.columnNames}" var="column">
                            <td>${data[column]}</td>
                        </c:forEach>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:set var="page_params" scope="session" value="tableName=${tableMetaData.tableName}" />
            <c:if test="${not empty page}">
              <%--  <tags:pagination page="${page}" paginationSize="${page.pageSize}"/>--%>
                <%
                    Page  pages=(Page)request.getAttribute("page");
                 /*   Page pag=new Page();*/
                    int current = pages.getPageNo() ;
                    long begin = Math.max(1, current - pages.getPageSize()/2);
                    long end = Math.min(begin + (pages.getPageSize() - 1), pages.getTotalPages());
                    request.setAttribute("current", current);
                    request.setAttribute("begin", begin);
                    request.setAttribute("end", end);
                %>
                <div class="pagination pagination-centered" style="width: 800px">
                    <ul>
                        <li class="disabled"><a>共${page.totalCount }条数据</a></li>

                        <% if ((pages.isHasNext() && current != 1) || (current == end && current != 1)){%>
                        <li><a href="${ctx}/enginessDatabase/toDatbaseUI.action?p=1&ps=${page.pageSize}&${page_params}">&lt;&lt;</a></li>
                        <li><a href="${ctx}/enginessDatabase/toDatbaseUI.action?p=${current-1}&ps=${page.pageSize}&${page_params}">&lt;</a></li>
                        <%}else{%>
                        <li class="disabled"><a href="#">&lt;&lt;</a></li>
                        <li class="disabled"><a href="#">&lt;</a></li>
                        <%} %>


                        <c:forEach var="i" begin="${begin}" end="${end}">
                            <c:choose>
                                <c:when test="${i == current}">
                                    <li class="active"><a href="${ctx}/enginessDatabase/toDatbaseUI.action?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="${ctx}/enginessDatabase/toDatbaseUI.action?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>



                        <% if (pages.isHasNext()){%>
                        <li><a href="?p=${current+1}&ps=${page.pageSize}&${page_params}">&gt;</a></li>
                        <li><a href="?p=${page.totalPages}&ps=${page.pageSize}&${page_params}">&gt;&gt;</a></li>
                        <%}else{%>
                        <li class="disabled"><a href="#">&gt;</a></li>
                        <li class="disabled"><a href="#">&gt;&gt;</a></li>
                        <%} %>
                    </ul>
                </div>

            </c:if>
            </c:if>

            <c:if test="${empty tableMetaData}">
                <p class="text-info text-center" style="margin-top: 50%;font-size: 20px;">单击左边的表名可以查看记录！</p>
            </c:if>
        </div>
    </div>
</body>
</html>