<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	function saveUIFromSubmit(){
		
		var index  =0;
		
		if(document.forms[0].account.value!=""){
			
			$.ajax({
				type: 'post',
				
				url: '${pageContext.request.contextPath }/user/UserServlet',
				
				data: {'method':'checkAccount','account':document.forms[0].account.value},
				
				success: function(result){
					var res = eval("("+result+")");
					if(res['statusId']==0){
						console.info(1222+"account");
						index++;
					}
					if(document.forms[0].username.value!=""){
						console.info(1222+"username");
						index++;
					}
					var regExp = /^[0-9]{11}$/;
					
					if(document.forms[0].phone.value!=""&&regExp.test(document.forms[0].phone.value)){
						console.info(1222+"phone");
						index++;
					}
				
					
					$(':radio').each(function(){
						console.info(111);
						if($(this).attr('checked')){
							console.info(1222+"gender");
							index++;
							
							//break;
						}
					});
					console.info(index);
					if(index==4){
						
						return true;
					}else{
						
						return false;
					}
					
				}
			});
			
		}
		
		//return false;
	}

	$(function(){
		
		$('#account').blur(function(){
			var account = $(this).val();
			if(account){
				
				$.ajax({
					type: 'post',
					
					url: '${pageContext.request.contextPath }/user/UserServlet',
					
					data: {'method':'checkAccount','account':account},
					
					success: function(result){
						
						var res = eval("("+result+")");
						
						if(res['statusId']==0){
							
							$('#showMsg').html("帐户可以使用");
						}else{
					
							
							$('#showMsg').html('帐户名已存在，建议使用<a href="javascript:void(0);" class="accountCssHref">'+account+parseInt(Math.random()*100)+'</a>');
						}
					}
				});
			}
		});
		
		//超链接是后面生成的元素，需要使用事件的委派
		$('.accountCssHref').live('click',function(){
			
			$('#account').val($('.accountCssHref').html());
		});
		
		$('#phone').blur(function(){
			
			var regExp = /^[0-9]{11}$/;
			
			if(regExp.test($(this).val())){
				
				$('#showPhoneMsg').html('手机号可以使用').css('color','green');
			}else{
				
				$('#showPhoneMsg').html('请检查手机号').css('color','red');
			}
		});
	});
</script>
</head>
<body>
<form action="${pageContext.request.contextPath }/user/UserServlet" method="post" onsubmit="return saveUIFromSubmit()">
	<input type="hidden" name="method" <c:if test="${editUser==null }"> value="save" </c:if><c:if test="${editUser!=null }"> value="edit" </c:if>/>
	<input type="hidden" name="uid" value="${editUser.uid }"/>
	姓名：<input type="text" name="username" value="${editUser.username }"/><span style="color:red;">*</span><br/>
	帐户名：<input type="text" name="account" id="account" value="${editUser.account }"/><span style="color:red;">*</span><span id="showMsg"></span><br/>
	性别：<input type="radio" name="gender" <c:if test="${editUser.gender=='男' }">checked</c:if> id="gen1" value="男"/><label for="gen1">男</label>
	<input type="radio" name="gender" <c:if test="${editUser.gender=='女' }">checked</c:if> id="gen2" value="女"/><label for="gen2">女</label><br/>
	手机号：<input type="text" name="phone" id="phone" value="${editUser.phone }"/><span id="showPhoneMsg"></span><br/>
	头像：<input type="file" name="headImg"/><br/>
	设置角色：<select name="roleid">
			<option value="">--请选择角色--</option>
			<c:forEach items="${rolelist }" var="role">
				<option <c:if test="${editUser.role.roleid==role.roleid }">selected</c:if> value="${role.roleid }">${role.rolename }</option>
			</c:forEach>
		</select><br/>
	&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="保存"/>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="返回"/>
</form>
</body>
</html>