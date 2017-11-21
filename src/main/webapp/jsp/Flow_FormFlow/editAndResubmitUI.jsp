<%--
  Created by IntelliJ IDEA.
  User: admi
  Date: 2017/8/8
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<HTML>
<HEAD>
    <TITLE>修改后再次提交</TITLE>
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8" />
    <SCRIPT LANGUAGE="javascript" SRC="${pageContext.request.contextPath}/script/jquery.js"></SCRIPT>
    <SCRIPT LANGUAGE="javascript" SRC="${pageContext.request.contextPath}/script/pageCommon.js" CHARSET="utf-8"></SCRIPT>
    <SCRIPT LANGUAGE="javascript" SRC="${pageContext.request.contextPath}/script/PageUtils.js" CHARSET="utf-8"></SCRIPT>
    <%--<SCRIPT LANGUAGE="javascript" SRC="${pageContext.request.contextPath}/script/DemoData.js" CHARSET="utf-8"></SCRIPT>
    <SCRIPT LANGUAGE="javascript" SRC="${pageContext.request.contextPath}/script/DataShowManager.js" CHARSET="utf-8"></SCRIPT>--%>
    <LINK TYPE="text/css" REL="stylesheet" HREF="${pageContext.request.contextPath}/style/blue/pageCommon.css" />
    <SCRIPT TYPE="text/javascript">
    </SCRIPT>
</HEAD>
<BODY>

<!-- 标题显示 -->
<DIV ID="Title_bar">
    <DIV ID="Title_bar_Head">
        <DIV ID="Title_Head"></DIV>
        <DIV ID="Title"><!--页面标题-->
            <IMG BORDER="0" WIDTH="13" HEIGHT="13" SRC="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 修改后再次提交
        </DIV>
        <DIV ID="Title_End"></DIV>
    </DIV>
</DIV>

<!--显示表单内容-->
<DIV ID=MainArea>
    <FORM ACTION="${pageContext.request.contextPath}/application/againApply.action" method="post" enctype="multipart/form-data">
        <input name="processInstanceId"  value="${requestScope.processInstanceId}" hidden>
        <DIV CLASS="ItemBlock_Title1"><!-- 信息说明 --><DIV CLASS="ItemBlock_Title1">
            <IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 相关信息 </DIV>
        </DIV>
        <DIV CLASS="ItemBlockBorder">
            <DIV CLASS="ItemBlock">
                <TABLE CELLPADDING="0" CELLSPACING="0" CLASS="mainForm">
                    <TR>
                        <TD WIDTH=" 150px">
                            <A HREF="${pageContext.request.contextPath}/" STYLE="text-decoration: underline">[点击下载表单模板文档]</A>
                        </TD>
                        <TD><A HREF="${pageContext.request.contextPath}" STYLE="text-decoration: underline">[点击下载申请文档]</A></TD>
                    </TR>
                </TABLE>
            </DIV>
        </DIV>

        <DIV CLASS="ItemBlock_Title1"><!-- 信息说明 --><DIV CLASS="ItemBlock_Title1">
            <IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 申请信息 </DIV>
        </DIV>
        <DIV CLASS="ItemBlockBorder">
            <DIV CLASS="ItemBlock">
                <TABLE CELLPADDING="0" CELLSPACING="0" CLASS="mainForm">
                    <TR>
                        <TD WIDTH="138">请选择修改后的表单文件</TD>
                        <TD><INPUT TYPE="file" NAME="resource" CLASS="InputStyle" STYLE="width:450px;" /> *</TD>
                    </TR>
                </TABLE>
            </DIV>
        </DIV>

        <!-- 表单操作 -->
        <DIV ID="InputDetailBar">
            <INPUT TYPE="image" SRC="${pageContext.request.contextPath}/style/blue/images/button/submit.PNG"/>
            <A HREF="javascript:history.go(-1);"><IMG SRC="${pageContext.request.contextPath}/style/images/goBack.png"/></A>
        </DIV>
    </FORM>
</DIV>

<DIV CLASS="Description">
</DIV>

</BODY>
</HTML>

