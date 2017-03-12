<%@page import="com.eduask.form.LoginForm"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();

	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title></title>
  <link rel="stylesheet" href="<%=basePath %>css/common.css" type="text/css"/>
  <style type="text/css">

    #logindiv{
      width: 600px;
      height: 400px;
      background-color: ghostwhite;
    }
    table{
      width: 100%;
      height: 70%;
    }
    tr>td:first-child{
      width: 40%;
      text-align: right;
    }
    tr>td:last-child{
      text-align: left;
    }
    .btn{
      width: 80px;
      height: 40px;
      background-color: orange;
      border: 0px;
    }
    .msg{
      font-size: 10px;
    }
  </style>
  <script type="text/javascript" src="<%=basePath %>js/jquery-1.7.2.js"></script>
  <script type="text/javascript" src="<%=basePath %>js/login.js"></script>
  <script type="text/javascript">
  	var id = 1;
  	function getCode(obj){
  		console.info(111);
  		var td = obj.parentNode;

 //var src1 = obj.src;
  	
  		obj.src = "img/CheckImgServlet?<%=new Date().getTime() %>";
  			
  		// td.innerHTML = "";
  		
  		/* if(src1.indexOf('?')==-1){
  			
  			td.innerHTML = '<img alt="" src="img/CheckImgServlet?" onclick="getCode(this)">';
  		}else{
  			
  			td.innerHTML = '<img alt="" src="img/CheckImgServlet" onclick="getCode(this)">';
  		} 
  		
  		id++; */
  		
  		
  	}
  </script>
</head>
<body style="background-color: orange">
	<%
		Cookie[] cookies = request.getCookies();
	
		String account = null;
		
		String pwd = null;
	
		if(cookies!=null){
			
			for(int i=0;i<cookies.length;i++){
				
				if("account".equals(cookies[i].getName())){
					
					account = cookies[i].getValue();
				}
				
				if("pwd".equals(cookies[i].getName())){
					
					pwd = cookies[i].getValue();
				}
				
			}
		}
	%>
  <div id="logindiv">
  <form action="${pageContext.request.contextPath }/user/UserServlet?method=loginUser" method="post">
    <table>
      <caption >登录表单</caption>
      <tr>
        <td><lable for="account">用户名<span style="color: red;">*</span>：</lable></td>
        <td><input type="text" name="account" value="<%=account==null?request.getAttribute("loginForm")==null?"":((LoginForm)request.getAttribute("loginForm")).getAccount():account %>" id="account" style="height: 23px;"/><span class="msg"></span></td>
      </tr>
      <tr>
        <td><lable for="pwd">密码<span style="color: red;">*</span>：</lable></td>
        <td><input type="text" name="pwd" id="pwd" value="<%=pwd==null?request.getAttribute("loginForm")==null?"":((LoginForm)request.getAttribute("loginForm")).getPwd():pwd %>" style="height: 23px;"/><span class="msg"></span></td>
      </tr>
      <tr>
        <td colspan="2"><input type="checkbox" id="rempwd" name="rempwd" value="true"/><lable for="rempwd">记住密码</lable></td>
      </tr>
      <tr>
      	<td>输入验证码：<input type="text" name="checkcode"/></td>
      	<td><img alt="" src="img/CheckImgServlet?<%=new Date().getTime() %>" onclick="getCode(this)"></td>
      </tr>
      <tr style="position: absolute;left: 210px">
        <td><input class="btn" type="submit" value="登录"/></td>
        <td><input class="btn" type="reset" value="重置"/></td>
      </tr>
    </table>
    </form>
  </div>
</body>
</html>