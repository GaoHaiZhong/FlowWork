<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看当前流程图</title>
</head>
<body>
<!-- 1.获取到规则流程图,获得流程图 -->
<img style="position: absolute;top: 0px;left: 0px;" src="${pageContext.request.contextPath}/processDefinitionController/seePng.action?processDefinitionId=${activityImpl.processDefinition.id}">

<!-- 2.根据当前活动的坐标，动态绘制DIV -->
<div style="position: absolute;border:1px solid red;top:${activityImpl.y}px;left: ${activityImpl.x}px;width: ${activityImpl.width}px;height:${activityImpl.height}px;   "></div></body>
</html>