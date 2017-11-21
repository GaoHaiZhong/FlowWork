<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.ghz.flow.base.util.Page" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%

    Page  pages=(Page)request.getAttribute("page");
    int current = pages.getPageNo() ;
    long begin = Math.max(1, current - pages.getPageSize()/2);
    long end = Math.min(begin + (pages.getPageSize() - 1), pages.getTotalPages());
    request.setAttribute("current", current);
    request.setAttribute("begin", begin);
    request.setAttribute("end", end);
%>
<div class="pagination pagination-centered" style="500px">
    <ul>
        <li class="disabled"><a>共${page.totalCount }条数据</a></li>
        <% if ((pages.isHasNext() && current != 1) || (current == end && current != 1)){%>
        <li><a href="${}?p=1&ps=${page.pageSize}&${page_params}">&lt;&lt;</a></li>
        <li><a href="?p=${current-1}&ps=${page.pageSize}&${page_params}">&lt;</a></li>
        <%}else{%>
        <li class="disabled"><a href="#">&lt;&lt;</a></li>
        <li class="disabled"><a href="#">&lt;</a></li>
        <%} %>


        <c:forEach var="i" begin="${begin}" end="${end}">
            <c:choose>
                <c:when test="${i == current}">
                    <li class="active"><a href="?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
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


</body>
</html>
