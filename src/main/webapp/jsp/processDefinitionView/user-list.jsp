<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.ghz.flow.base.util.Page" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/jsp/Base/global.jsp"%>
    <%@ include file="/jsp/Base/meta.jsp" %>
    <%@ include file="/jsp/Base/include-base-styles.jsp" %>

    <script src="${ctx }/js/common/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx }/js/common/bootstrap.min.js"></script>
    <title>用户列表--chapter14</title>
    <script type="text/javascript">
        $(function() {
            $('.edit-user').click(function() {
                var $tr = $('#' + $(this).data('id'));
                $('#userId').val($tr.find('.prop-id').text());
                $('#firstName').val($tr.find('.prop-firstName').text());
                $('#lastName').val($tr.find('.prop-lastName').text());
                $('#email').val($tr.find('.prop-email').text());
            });

            // 组提示
            $('.groups').tooltip();

            // 点击按钮设置用户的组
            $('.set-group').click(function() {


                $('#setGroupsModal input[name=userId]').val($(this).data('userid'));
                $('#setGroupsModal input[name=group]').attr('checked', false);
                var date=$(this).data('groupids');
                var  groupIds="";
                var sear=new RegExp(',');

                if(sear.test(date)){
                    groupIds =date.split(",");
                }else {
                    groupIds = date;
                }
                var length=groupIds.length;
                if(length>0){
                    for(var i=0;i<length;i++){
                        $('#setGroupsModal input[name=group][value=' + groupIds[i]+ ']').attr('checked', true);
                    }

                }else{
                    //当一个时为未定义
                    $(groupIds).each(function() {
                        $('#setGroupsModal input[name=group][value=' + this + ']').attr('checked', true);
                    });
                }
                $('#setGroupsModal').modal();
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-pills">
    <li class="active"><a href="${ctx}/identity/toUserList.action"><i class="icon-user"></i>用户管理</a></li>
    <li><a href="${ctx}/identity/toRollerList.action"><i class="icon-list"></i>组管理</a></li>
</ul>
<hr>
<c:if test="${not empty message}">
    <div id="message" class="alert alert-success">${message}</div>
    <!-- 自动隐藏提示信息 -->
    <script type="text/javascript">
        setTimeout(function() {
            $('#message').hide('slow');
        }, 5000);
    </script>
</c:if>
<div class="row">
    <div class="span8">
        <fieldset>
            <legend><small>用户列表</small></legend>
            <table width="100%" class="table table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th>用户ID</th>
                    <th>姓名</th>
                    <th>Email</th>
                    <th>所属组</th>
                    <th width="130">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.result }" var="user">
                    <tr id="${user.id}">
                        <td class="prop-id">${user.id}</td>
                        <td>
                                ${user.loginname}
                            <span class="prop-firstName" style="display: none">${user.loginname}</span>
                            <%--<span class="prop-lastName" style="display: none">${user.lastName}</span>--%>
                        </td>
                        <td class="prop-email">为空</td>
                        <%----%>
                        <c:set var="groupNames" value="${''}" />
                        <c:set var="groupIds" value="${''}" />
                        <c:forEach items="${groupOfUserMap[user.id]}" var="group" varStatus="row">
                            <c:set var="groupNames" value="${groupNames}${group.rolername}" />
                            <c:set var="groupIds" value="${groupIds}${group.id}" />
                            <c:if test="${row.index + 1 < fn:length(groupOfUserMap[user.id])}">
                                <c:set var="groupNames" value="${groupNames}${','}" />
                                <c:set var="groupIds" value="${groupIds}${','}" />
                            </c:if>
                        </c:forEach>
                        <c:if test="${empty groupNames}">
                            <c:set var="groupNames" value="${'还未设置所属组，请单击设置'}" />
                        </c:if>
                        <td title="${groupNames}" class="groups">
                            <a href="#" class="set-group" data-groupids="${groupIds}" data-userid="${user.id}" data-toggle="modal">共${fn:length(groupOfUserMap[user.id])}个组</a><br>
                        </td>
                        <td>
                            <a class="btn btn-danger btn-small" href="${ctx}/identity//deleteUser/${user.id}"><i class="icon-remove"></i>删除</a>
                            <a class="btn btn-info btn-small edit-user" data-id="${user.id}" href="#"><i class="icon-pencil"></i>编辑</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
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
            <div class="pagination pagination-centered" >
                <ul>
                    <li class="disabled"><a>共${page.totalCount }条数据</a></li>

                    <% if ((pages.isHasNext() && current != 1) || (current == end && current != 1)){%>
                    <li><a href="${ctx}/identity/toUserList.action?p=1&ps=${page.pageSize}&${page_params}">&lt;&lt;</a></li>
                    <li><a href="${ctx}/identity/toUserList.action?p=${current-1}&ps=${page.pageSize}&${page_params}">&lt;</a></li>
                    <%}else{%>
                    <li class="disabled"><a href="#">&lt;&lt;</a></li>
                    <li class="disabled"><a href="#">&lt;</a></li>
                    <%} %>


                    <c:forEach var="i" begin="${begin}" end="${end}">
                        <c:choose>
                            <c:when test="${i == current}">
                                <li class="active"><a href="${ctx}/identity/toUserList.action?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="${ctx}/identity/toUserList.action?p=${i}&ps=${page.pageSize}&${page_params}">${i}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <% if (pages.isHasNext()){%>
                    <li><a href="${ctx}/identity/toUserList.action?p=${current+1}&ps=${page.pageSize}&${page_params}">&gt;</a></li>
                    <li><a href="${ctx}/identity/toUserList.action?p=${page.totalPages}&ps=${page.pageSize}&${page_params}">&gt;&gt;</a></li>
                    <%}else{%>
                    <li class="disabled"><a href="#">&gt;</a></li>
                    <li class="disabled"><a href="#">&gt;&gt;</a></li>
                    <%} %>
                </ul>
            </div>

        </fieldset>
    </div>

    <!-- 新增、编辑用户的Model -->
    <div class="span4">
        <form action="${ctx }/identity/saveUser.action" class="form-horizontal" method="post">
            <fieldset>
                <legend><small>新增/编辑用户</small></legend>
                <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
                <div class="control-group">
                    <label for="userId" class="control-label">用户ID:</label>
                    <div class="controls">
                        <input type="text" id="userId" name="id" class="required" />
                    </div>
                </div>
                <%--<div class="control-group">
                    <label for="lastName" class="control-label">姓:</label>
                    <div class="controls">
                        <input type="text" id="lastName" name="lastName" class="required" />
                    </div>
                </div>--%>
               <div class="control-group">
                    <label for="firstName" class="control-label">姓名:</label>
                    <div class="controls">
                        <input type="text" id="firstName" name="loginname" class="required" />
                    </div>
                </div>
                <div class="control-group">
                    <label for="email" class="control-label">Email:</label>
                    <div class="controls">
                        <input type="text" id="email" name="email" class="required" />
                    </div>
                </div>
                <div class="control-group">
                    <label for="password" class="control-label">密码:</label>
                    <div class="controls">
                        <input type="password" id="password" name="password" class="required" />
                    </div>
                </div>
                <div class="form-actions">
                    <button type="reset" class="btn"><i class="icon-remove"></i>重置</button>
                    <button type="submit" class="btn btn-primary"><i class="icon-ok-sign"></i>保存</button>
                </div>
            </fieldset>
        </form>
    </div>

    <div id="setGroupsModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="setGroupsModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="setGroupsModalLabel">设置用户的所属组</h3>
        </div>
        <div class="modal-body">
            <form action="${ctx}/identity/setRoler.action" method="POST">
                <input type="hidden" name="userId" />
                <div class="row">
                    <div class="span3">
                        <c:forEach items="${allGroup}" var="group">
                            <label class="checkbox"><input type="checkbox" name="group" value="${group.id}" />${group.rolername}</label>
                        </c:forEach>
                    </div>
                    <div class="span2">
                        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                        <button type="submit" class="btn btn-primary">确定添加</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>