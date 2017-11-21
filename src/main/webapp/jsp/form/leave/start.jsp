<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/jsp/Base/global.jsp"%>
	<%@ include file="/jsp/Base/meta.jsp" %>
	<%@ include file="/jsp/Base/include-base-styles.jsp" %>
	<link rel="stylesheet" href="${ctx}/js/common/plugins/timepicker.css">
	<title>请假申请</title>
	<script type="text/javascript" src="${ctx }/js/common/jquery.js"></script>
	<script type="text/javascript" src="${ctx }/js/common/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx }/js/common/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${ctx }/js/common/plugins/bootstrap-timepicker.js"></script>
	<script type="text/javascript">
	$(function() {
		$('.datepicker').datepicker();
		$('.time').timepicker({
			minuteStep: 10,
            showMeridian: false
		});
	});

	function beforeSend() {
		$('input[name=startTime]').val($('#startDate').val() + ' ' + $('#startTime').val());
		$('input[name=endTime]').val($('#endDate').val() + ' ' + $('#endTime').val());
	}
	</script>
</head>
<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">${message}</div>
		<!-- 自动隐藏提示信息 -->
		<script type="text/javascript">
		setTimeout(function() {
			$('#message').hide('slow');
		}, 5000);
		</script>
	</c:if>
	<form action="${pageContext.request.contextPath}/application/upFile.action" class="form-horizontal" method="post" onsubmit="beforeSend()" enctype="multipart/form-data" >
		<input type="hidden" name="startTime" />
		<input type="hidden" name="endTime" />
		<input type="hidden" name="templateId"  value="${requestScope.templateId}"/>
		<fieldset>
			<legend><small>请假申请</small></legend>
			<div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
			<div class="control-group">
				<label name="loginName" class="control-label">请假类型:</label>
				<div class="controls">
					<select id="leaveType" name="leaveType" class="required">
						<option>公休</option>
						<option>病假</option>
						<option>调休</option>
						<option>事假</option>
						<option>婚假</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label name="name" class="control-label">开始时间:</label>
				<div class="controls">
					<input type="text" id="startDate" class="datepicker input-small" data-date-format="yyyy-mm-dd" />
					<input type="text" id="startTime" class="time input-small" />
				</div>
			</div>
			<div class="control-group">
				<label name="plainPassword" class="control-label">结束时间:</label>
				<div class="controls">
					<input type="text" id="endDate" class="datepicker input-small" data-date-format="yyyy-mm-dd" />
					<input type="text" id="endTime" class="time input-small" />
				</div>
			</div>
			<div class="control-group">
				<label name="groupList" class="control-label">请假原因:</label>
				<div class="controls">
					<textarea name="reason"></textarea>
				</div>
			</div>
			<div class="ItemBlockBorder">
				<div class="ItemBlock">
					<table cellpadding="0" cellspacing="0" class="mainForm">
						<tr>
							<td width="130">请选择填写好的附件</td>
							<td><input type="file" name="resource" class="InputStyle" style="width:450px;" /> *</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="form-actions">
				<button type="submit" class="btn"><i class="icon-play"></i>启动流程</button>
				<button type="submit" class="btn"><i class="icon-play"></i>返回模板列表</button>
			</div>
		</fieldset>
	</form>
</body>
</html>