<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>提交申请</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/pageCommon.css" />
    <script type="text/javascript">
    </script>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 提交申请
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id=MainArea>
    <form action="${pageContext.request.contextPath}/application/upFile.action" method="post" enctype="multipart/form-data">
       <input type="text" name="templateId"  value="${id}" hidden/>
        <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
            <img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 下载文档模板 </div>
        </div>
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/fromTemplate/upload.action?id=${id}" style="text-decoration: underline">[点击下载文档模板文件]</a></td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td><a href="${pageContext.request.contextPath}/application/toFormUI.action?id=${id}&pdkey=${pdkey}" style="text-decoration: underline">点击填写表单信息</a></td>
                    </tr>
                </table>
            </div>
        </div>

       <%-- <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
            <img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" />附件上传</div>
        </div>--%>

       <%-- <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                        <td width="130">请选择填写好的附件</td>
                        <td><input type="file" name="resource" class="InputStyle" style="width:450px;" /> *</td>
                    </tr>
                </table>
            </div>
        </div>--%>

        <!-- 表单操作 -->
       <%-- <div id="InputDetailBar">
            <input type="image" src="${pageContext.request.contextPath}/style/blue/images/button/submit.PNG"/>
            <a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/style/images/goBack.png"/></a>
        </div>--%>
    </form>
</div>

<div class="Description">
    使用说明：<br />
    1，下载模板文件。<br />
    2，填写文档中的表单。<br />
    3，选择这个填写好的文件进行提交。<br />
    <br />
    说明2：提交表单后，就会按照 模板对应的流程 开始流转。
</div>

</body>
</html>
