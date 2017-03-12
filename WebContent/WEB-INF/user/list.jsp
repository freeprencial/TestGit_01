<%@page import="com.eduask.util.PageUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'userMgr.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(function(){
		
		$('input[name=enable]').click(function(){
			
			var enId= $(this).parent().parent().attr('id');
			
			location.href= "${pageContext.request.contextPath }/user/UserServlet?method=enableUser&id="+enId;
		});
		$('input[name=disable]').click(function(){
			
			var disId = $(this).parent().parent().attr('id');
			location.href= "${pageContext.request.contextPath }/user/UserServlet?method=disableUser&id="+disId;
		});
	
		$('#all').click(function(){
			
			$('input[name=uid]').attr('checked',this.checked);
		});
		
		$('input[name=uid]').click(function(){
			
			var index = -1;
			
			$('input[name=uid]').each(function(i){
				
				if(!$(this).attr('checked')){
					
					index = i;
				}
			});
			
			if(index==-1){
				
				$('#all').attr('checked',true);
			}else{
				
				$('#all').attr('checked',false);
			}
		});
	});
	
	function del(){
		
		return window.confirm("是否确认删除该用户?");
	}
	
	function deleteUids(){
		
		var flag = confirm("是否批量删除选中的用户？");
		
		if(flag){
			
			$(':hidden').val('deleteUids');
			
			
			$(document.forms[0]).submit();
		}
		
	}
	
	function initpwdUids(){
		
		
	var flag = confirm("是否批量初始化选中的用户的密码？");
		
		if(flag){
			
			$(':hidden').val('initpwdUids');
			
			
			$(document.forms[0]).submit();
		}
	}
	
	function saveCheckUid(){
		
		var uids = "";
		
		$('input[name=uid]').each(function(){
			
			if($(this).attr('checked')){
				
				uids += $(this).val()+'-';
			}
		});
		
		uids = uids.substr(0,uids.length-1);
		
		if(uids.length!=0){
			
			$.post('${pageContext.request.contextPath }/user/UserServlet',{'method':'saveCheckedUid','uids':uids},function(result){
				
				console.info("success");
			});
			
		}

	}
</script>
  </head>
  
  <body>
   <div style="width: 100%;height: 100px;text-align: center;">
   <form action="user/UserServlet?method=list&pageIndex=1" method="post">
   用户名：<input type="search" name="account" value="${account }"/>
   姓名：<input type="search" name="username" value="${username }"/><br>
   部门：<select name="department">
   	<option value="">--所有部门--</option>
   	<c:forEach items="${departments }" var="department1">
   		<option value="${department1.id }"  <c:if test="${department==department1.id }">selected</c:if>>${department1.name }</option>
   	</c:forEach>
	</select>
性别：<select name="sex">
	<option value="">--全部--</option>
	<option value="男" <c:if test="${gender=='男' }">selected</c:if>>男</option>
	<option value="女" <c:if test="${gender=='女' }">selected</c:if>>女</option>
	</select>
状态：<input type="radio" checked name="disabled" <c:if test="${disabled=='0' }">checked</c:if> value="0"/>--全部--
<input type="radio" name="disabled" value="-1" <c:if test="${disabled=='-1' }">checked</c:if>/>禁用
<input type="radio" name="disabled" value="1" <c:if test="${disabled=='1' }">checked</c:if>/>启用
<input type="submit" value="search" id="search" />
</form>
   </div>
   <div align="right"><a href="javascript:void(0);" onclick="deleteUids()">批量删除</a>|<a href="javascript:void(0);" onclick="initpwdUids()">批量初始化密码</a>|<a href="user/UserServlet?method=saveUI">新增用户</a></div>
   <form action="${pageContext.request.contextPath }/user/UserServlet">
   <table border="1" style="width: 100%">
   		<thead>
   		<th><input type="hidden" name="method"/><input type="checkbox" id="all"/></th>
   		<th>用户名</th>
		<th>真实姓名</th>
		<th>性别</th>
		<th>岗位</th>
		<th>手机号</th>
		<th>状态</th>
		<th>操作</th>
   		</thead>
   		<tbody>
   		<!-- EL表达式 -->
   		<c:forEach items="${userlist }" var="user">
   			<tr id="${user.uid }">
   				<td><input type="checkbox" name="uid" value="${user.uid }"/></td>
	   			<td>${user.account }</td>
	   			<td>${user.username }</td>
	   			<td>${user.gender }</td>
	   			<td>${user.role.rolename }</td>
	   			<td>${user.phone }</td>
	   			<td><input type="button" name="enable" <c:if test="${user.accountEnable.enableId==1 }">disabled</c:if> value="启用"><input type="button" name="disable" <c:if test="${user.accountEnable.enableId==-1 }">disabled</c:if> value="禁用"></td>
	   			<td><a href="${pageContext.request.contextPath }/user/UserServlet?method=delete&id=${user.uid }" onclick="return del()">删除</a>
	   			&nbsp;<a href="${pageContext.request.contextPath }/user/UserServlet?method=editUI&id=${user.uid }">修改</a>
	   			&nbsp;<a href="${pageContext.request.contextPath }/user/UserServlet?method=initpwd&id=${user.uid }" onclick="return confirm('初始化密码为1234?')">初始化密码</a></td>
   			</tr>
   		</c:forEach>
   		
   		</tbody>
   </table>
   </form>
   <div style="width: 100%;height: 50px;text-align: center;">
   共${pageUtil.totalPage }页&nbsp;&nbsp; <% for(int i=1;i<=((PageUtil)request.getAttribute("pageUtil")).getTotalPage();i++){ %>&nbsp;<a href="${pageContext.request.contextPath }/user/UserServlet?method=list&pageIndex=<%=i %>" onclick="saveCheckUid()"><%=i %></a><%} %>&nbsp;
   </div>
  </body>
</html>
