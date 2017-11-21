<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>ItcastOA</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<frameset rows="100,*,25" framespacing="0" border="0" frameborder="0">
    <frame src="${pageContext.request.contextPath}/jsp/home/top.jsp" name="TopMenu" scrolling="no" noresize />
    <frameset cols="180,*" id="resize">
        <frame noresize name="menu" src="${pageContext.request.contextPath}/jsp/home/left.jsp" scrolling="yes" />
        <frame noresize name="right" src="${pageContext.request.contextPath}/jsp/home/right.jsp" scrolling="yes" />
    </frameset>
    <frame noresize name="status_bar" scrolling="no" src="${pageContext.request.contextPath}/jsp/home/bottom.jsp" />
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>
