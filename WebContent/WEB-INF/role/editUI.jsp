<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.purviewDiv{
		float: left;
		width: 33%;
		height: 300px;
	}
	#btn{
		clear: left;
	}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(function(){
		
		//console.info($('.purviewDiv>input[name=purid]'));
		
		$('.purviewDiv>input[name=purid]').click(function(){
			
			console.info($(this).next('ul').find(':checkbox'));
			
			$(this).next('ul').find(':checkbox').attr('checked',this.checked);
		});
		$('li').find(':checkbox').click(function(){
			
			$(this).parent().parent().prev('input').attr('checked',true);
		});
		
		$('#btn').click(function(){
			//编辑界面
			if($('input[name=rolename]').length==0){
				$('#roleform').submit();
				
			}else{
				//新增页面
				var rolename = $('input[name=rolename]').val();
				
				if(rolename){
					
					$.ajax({
						
						type: 'post',
						
						url: '${pageContext.request.contextPath}/role/RoleServlet',
						
						data:{'method':'checkRoleName','rolename':rolename},
						
						success: function(result){
							
							result = eval('('+result+')');
							
							if(result['statusId']==1){
								
								$('#roleform').submit();
								
							}else{
								$('#rolenameMsg').html(result['msg']);
							}
						}
					});
				}
				
			}
			 
			
			
		});
		
	});
	
	function check(){
		
		var rolename = $('input[name=rolename]').val();
		
		if(rolename){
			
			$.ajax({
				
				type: 'post',
				
				url: '${pageContext.request.contextPath}/role/RoleServlet',
				
				data:{'method':'checkRoleName','rolename':rolename},
				
				success: function(result){
					
					result = eval('('+result+')');
					
					if(result['statusId']==1){
						
					}else{
						$('#rolenameMsg').html(result['msg']);
					}
				}
			});
		}
		
	}
</script>
</head>
<body>
<div>角色名称：${role.rolename==null?'添加新角色':role.rolename }</div>
<div>
<form id="roleform" 
	<c:if test="${type=='edit' }"> 
		action="${pageContext.request.contextPath }/role/RoleServlet?method=edit"
	</c:if> 
	<c:if test="${type==null }"> 
		action="${pageContext.request.contextPath }/role/RoleServlet?method=add"
	</c:if>
	 method="post">
<br/>
<c:if test="${role!=null }"><input type="hidden" name="roleid" value="${role.roleid }"/></c:if>
<c:if test="${role.rolename==null }">请输入角色名称：<input type="text" name="rolename" onblur="check()"/><span id="rolenameMsg"></span></c:if>
<br/>
<c:forEach items="${purviewlist }" var="purview">
	
<div class="purviewDiv">
	<input type="checkbox" name="purid" <c:if test="${type=='see' }">disabled</c:if> 
		<c:forEach items="${role.purviewList }" var="rolepurview">
			<c:if test="${purview.purId==rolepurview.purId }">checked</c:if>
		</c:forEach> 
	value="${purview.purId }"/>${purview.purname }
	<ul>
		<c:forEach items="${purview.childrenPurviews }" var="childpurview">
			
			<li>
				<input type="checkbox" name="purid" <c:if test="${type=='see' }">disabled</c:if>
				<c:forEach items="${role.purviewList }" var="rolepurview">
					 <c:forEach items="${rolepurview.childrenPurviews }" var="rolechildpurview">
					 	<c:if test="${childpurview.purId==rolechildpurview.purId }">checked</c:if>
					 </c:forEach>
					</c:forEach>
				 value="${childpurview.purId }"/>${childpurview.purname }
			</li>
			
		</c:forEach>
	</ul>
</div>
	
</c:forEach>
<c:if test="${type==null||type=='edit' }">
	<input id="btn" type="button" value="保存角色"/>
</c:if>
<c:if test="${type=='see' }">
	<input id="btn1" type="button" onclick="javascript:location.href='${pageContext.request.contextPath }/role/RoleServlet?method=list'" value="返回"/>
</c:if>
</form>

<br/>
</body>
</html>