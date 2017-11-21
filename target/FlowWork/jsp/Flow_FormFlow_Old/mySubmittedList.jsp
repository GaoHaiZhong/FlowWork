<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.ghz.flow.base.util.Page" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<html>
<head>
    <title>我的申请查询</title>
   <%-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>--%>
   <script language="javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
 <script language="javascript" src="${pageContext.request.contextPath}/script/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="${pageContext.request.contextPath}/script/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/blue/pageCommon.css" />

    <%@ include file="/jsp/Base/global.jsp"%>
    <%@ include file="/jsp/Base/meta.jsp" %>
    <%@ include file="/jsp/Base/include-base-styles.jsp" %>
    <title>申请</title>
    <link rel="stylesheet" href="${ctx }/js/common/plugins/datetimepicker/bootstrap-datetimepicker.min.css">

    <script src="${ctx }/js/common/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx }/js/common/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${ctx }/js/common/plugins/datetimepicker/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript">
        var province_2 ="${status}";
        $("#SelectStyle option[value='"+province_2+"']").attr("selected",true);
    </script>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 我的申请查询
        </div>
        <div id="Title_End"></div>
    </div>
</div>


<div id="QueryArea">
    <div style="height: 30px">
        <form action="${pageContext.request.contextPath}/application/getApplicationList.action" method="post">
            <table border=0 cellspacing=3 cellpadding=5>
                <tr>
                    <td><select name="status" id="SelectStyle" >
                        <option value="">查看全部状态</option>
                        <option value="审批中">审批中</option>
                        <option value="未通过">未通过</option>
                        <option value="已通过">已通过</option>
                    </select>
                    </td>
                    <td><a href=""><input type="IMAGE" src="${pageContext.request.contextPath}/style/blue/images/button/query.PNG"/></a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div id="MainArea">
    <%--<table cellspacing="0" cellpadding="0" class="TableStyle">
        <!-- 表头-->
        <thead>
        <tr align="CENTER" valign="MIDDLE" id="TableTitle">
            <td width="30px">序号</td>
            <td width="250px">标题</td>
            <td width="115px">申请人</td>
            <td width="115px">申请日期</td>
            <td width="115px">当前状态</td>
            <td>相关操作</td>
        </tr>
        </thead>

        <!--显示数据列表：正在审批或审批完成的表单显示示例-->
        <tbody id="TableData" class="dataContainer" datakey="formList">
        <!-- 正在审批或审批完成的表单显示示例 -->
        <c:forEach items="${list}" var="leaveBean" varStatus="status">

            <tr class="TableDetail1 template">
                <td  id="page">
                 &lt;%&ndash; ${status.index + 1}(${page.pageNo*10}) ${status.count}&ndash;%&gt;
                         ${leaveBean.historicTaskInstanceEntity.processInstanceId}
                </td>
                <td><a href="${pageContext.request.contextPath}/Flow_Formflow/showForm.html">${leaveBean.application.title}</a></td>
               &lt;%&ndash; <td>${user.loginname}&nbsp;</td>&ndash;%&gt;
                <td>${sessionScope.user.loginname}</td>
                <td>
                   &lt;%&ndash; 日期格式化。格式化日期&ndash;%&gt;
                    <fmt:formatDate value="${leaveBean.application.applydate}" pattern="yyyy-MM-dd HH:mm" />
                </td>
                <td>${leaveBean.application.status}&nbsp;</td>
                <td><a href="${pageContext.request.contextPath}/Flow_Formflow/showForm.html">查看申请信息</a>
                    <a href="${pageContext.request.contextPath}/leaveMail/toApprovedHistoryUI.action?processInstanceId=${leaveBean.historicTaskInstanceEntity.processInstanceId}">查看流转记录</a>
                </td>
            </tr>

        </c:forEach>--%>
        //测试

     </tbody>
    </table>
       <div class="cntainer">
           <div class="row" style="margin-left: 10px">
               <div class="col-md-12">
                   <table class="table table-honver">
                       <tr>
                           <td>序号</td>
                           <td>标题</td>
                           <td>申请人</td>
                           <td>申请日期</td>
                           <td>当前状态</td>
                       </tr>
                       <c:forEach items="${pageList}" var="flowBean" varStatus="status">
                           <tr>
                               <td><span style="color: red">${status.index+1}</span>||${flowBean.historicTaskInstanceEntity.processInstanceId }</td>
                               <td>${flowBean.application.title}</td>
                              <td>${user.loginname}&nbsp;</td>
                               <td>
                                   <fmt:formatDate value="${flowBean.application.applydate}" pattern="yyyy-MM-dd HH:mm" />
                               </td>
                               <td>${flowBean.application.status}&nbsp;</td>

                               <td>


                                   <a href="${pageContext.request.contextPath}/Flow_Formflow/showForm.html"> <button class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-pencil"></span>查看申请信息</button></a>
                               </td>
                               <td>
                                   <a href="${pageContext.request.contextPath}/leaveMail/toApprovedHistoryUI.action?processInstanceId=${flowBean.historicProcessInstanceEntity.id}">
                                       <button class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-trash"></span>查看流转记录</button></a>
                               </td>
                               <c:if test="${not empty flowBean.taskEntity.id }">
                               <td>
                                   <a href="${pageContext.request.contextPath}/leaveMail/followPircuter.action?id=${flowBean.taskEntity.id }">
                                       <button class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-trash"></span>图像跟踪</button></a>
                               </td>
                               </c:if>
                           </tr>
                       </c:forEach>
                   </table>
               </div>
           </div>

           <!-- 分页 -->
           <div class="row" style="margin-left: 10px">
               <div class="col-md-6">
                   当前${pageInfo.pageNum}页，总共${pageInfo.pages }页，总共${pageInfo.total }条记录
               </div>
               <div class="col-md-6">
                   <nav aria-lable="Page navigation">
                       <ul class="pagination">

                           <li><a href="${ctx}/application/getApplicationList.action?pageNum=1&status=${status}">首页</a></li>

                           <c:if test="${pageInfo.hasPreviousPage}">
                               <li>
                                   <a href="${ctx}/application/getApplicationList.action?pageNum=${pageInfo.pageNum-1}&status=${status}" aria-label="Previous">
                                       <span aria-hidden="true">&laquo;</span>
                                   </a>
                               </li>
                           </c:if>

                           <c:forEach items="${pageInfo.navigatepageNums  }" var="page">
                               <c:if test="${page==pageInfo.pageNum }">
                                   <li class="active"><a href="${ctx}/application/getApplicationList.action?pageNum=${page}&status=${status}">${page}</a></li>
                               </c:if>
                               <c:if test="${page!=pageInfo.pageNum }">
                                   <li><a href="${ctx}/application/getApplicationList.action?pageNum=${page}&status=${status}">${page}</a></li>
                               </c:if>
                           </c:forEach>

                           <c:if test="${pageInfo.hasNextPage }">
                               <li>
                                   <a href="${ctx}/application/getApplicationList.action?pageNum=${pageInfo.pageNum+1 }&status=${status}" aria-label="Next">
                                       <span aria-hidden="true">&raquo;</span>
                                   </a>
                               </li>
                           </c:if>

                           <li><a href="${ctx}/application/getApplicationList.action?pageNum=${pageInfo.pages}&status=${status}">末页</a></li>

                       </ul>
                   </nav>
               </div>
           </div>
       </div>



    <!-- 分页 -->


<%--被驳回的申请--%>
    <div id="Title_bar">
        <div id="Title_bar_Head">
            <div id="Title_Head"></div>
            <div id="Title"><!--页面标题-->
                <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/>我的被驳回的申请
            </div>
            <div id="Title_End"></div>
        </div>
    </div>
    <div id="MainArea">
        <table cellspacing="0" cellpadding="0" class="TableStyle">
         <%--   <table border=0 cellspacing=3 cellpadding=5>--%>
            <!-- 表头-->
            <thead>
            <tr align="CENTER" valign="MIDDLE" id="TableTitle">
                <td width="250px">标题</td>
                <td width="115px">申请人</td>
                <td width="115px">申请日期</td>
                <td width="115px">当前状态</td>
                <td>相关操作</td>
            </tr>
            </thead>
            <%-- 显示数据列表：被退回的我的表的单显示示例--%>
             <tbody id="TableData" class="dataContainer" datakey="formList">

            <%-- 被退回的我的表的单显示示例--%>
                 <c:forEach items="${unlist}" var="LeaveBean" >
                 <tr class="TableDetail1 template">
                     <td><a href="${pageContext.request.contextPath}/Flow_Formflow/showForm.html">${LeaveBean.application.title}</a></td>
                     <td>${sessionScope.user.loginname}&nbsp;</td>
                     <td>
                             <%-- 日期格式化。格式化日期--%>
                         <fmt:formatDate value="${LeaveBean.application.applydate}" pattern="yyyy-MM-dd HH:mm" />
                     </td>
                     <td>${LeaveBean.application.status}&nbsp;</td>
                     <td><a href="${pageContext.request.contextPath}/Flow_Formflow/showForm.html">查看申请信息</a>
                         <a href="${pageContext.request.contextPath}/approve/toApprovedHistoryUI.action?processInstanceId=${LeaveBean.historicTaskInstanceEntity.processInstanceId}">查看流转记录</a>
                         <a href="${pageContext.request.contextPath}/application/updateApplyUI.action?processInstanceId=${LeaveBean.historicTaskInstanceEntity.processInstanceId}">修改后再次提交</a>
                         <a href="#" onClick="return delConfirm()">删除</a>
                     </td>
                 </tr>
    </c:forEach>
             </tbody>
  </table>



    <!-- 其他功能超链接 -->
    <div id="TableTail"><div id="TableTail_inside"></div></div>
</div>
</body>
</html>
