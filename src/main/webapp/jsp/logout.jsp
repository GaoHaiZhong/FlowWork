<%--
    这里将logout.jsp注销页面，改为登陆页面
  User: admi
  Date: 2017/6/19
  Time: 15:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HTML>
<HEAD>
	<META http-equiv=Content-Type CONTENT="text/html; charset=UTF-8" />
	<TITLE>Itcast OA</TITLE>
	<LINK HREF="${pageContext.request.contextPath}/style/blue/login.css" type=text/css rel=stylesheet />
</HEAD>

<BODY LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 CLASS=PageBody >
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
