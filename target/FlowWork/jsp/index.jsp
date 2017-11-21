<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
<HEAD>
    <META http-equiv=Content-Type CONTENT="text/html; charset=UTF-8" />
    <TITLE>Itcast OA</TITLE>
    <LINK HREF="${pageContext.request.contextPath}/style/blue/login.css" type=text/css rel=stylesheet />
</HEAD>

<BODY LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 CLASS=PageBody >

<spring:hasBindErrors name="user">
    <c:forEach items="${errors.allErrors}" var="error">
        ${error.defaultMessage}<br/>
    </c:forEach>
</spring:hasBindErrors>
<spring:hasBindErrors name="user">
    <c:if test="${errors.fieldErrorCount > 0}">
        字段错误：
        <c:forEach items="${errors.fieldErrors}" var="error">
            <spring:message var="message" code="${error.code}" arguments="${error.arguments}" text="${error.defaultMessage}"/>
            ${error.field}------${message}
        </c:forEach>
    </c:if>

    <c:if test="${errors.globalErrorCount > 0}">
        全局错误：
        <c:forEach items="${errors.globalErrors}" var="error">
            <spring:message var="message" code="${error.code}" arguments="${error.arguments}" text="${error.defaultMessage}"/>
            <c:if test="${not empty message}">
                ${message}
            </c:if>
        </c:forEach>
    </c:if>
</spring:hasBindErrors>
<FORM METHOD="post" NAME="actForm" ACTION="${pageContext.request.contextPath}/login.action">
    <DIV ID="CenterAreaBg">
        <DIV ID="CenterArea">
            <DIV ID="LogoImg">工作流系统</DIV>
            <DIV ID="LoginInfo">
                <TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 width=100%>
                    <TR>
                        <TD width=45 CLASS="Subject"><IMG BORDER="0" SRC="${pageContext.request.contextPath}/style/blue/images/login/userId.gif" /></TD>
                        <TD><INPUT SIZE="20" CLASS="TextField" TYPE="text" NAME="loginname" /></TD>
                        <TD ROWSPAN="2" STYLE="padding-left:10px;"><INPUT TYPE="image" SRC="${pageContext.request.contextPath}/style/blue/images/login/userLogin_button.gif"/></TD>
                    </TR>
                    <TR>
                        <TD CLASS="Subject"><IMG BORDER="0" SRC="${pageContext.request.contextPath}/style/blue/images/login/password.gif" /></TD>
                        <TD><INPUT SIZE="20" CLASS="TextField" TYPE="password" NAME="password" /></TD>
                    </TR>
                </TABLE>
            </DIV>
            <DIV ID="CopyRight"><A HREF="javascript:void(0)">&copy; 2010 版权所有 </A></DIV>
        </DIV>
    </DIV>
</FORM>
</BODY>

</HTML>
