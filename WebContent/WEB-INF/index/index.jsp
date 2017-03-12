<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="css/indexi.css" type="text/css"/>
    <link rel="stylesheet" href="css/common.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
    <script src="js/index.js" type="text/javascript" charset="utf-8"></script>
</head>
<body >
<div class="nav">
    <div class="logo">oa管理</div>
    <div class="div1">登出</div>
    <div class="div1" id="user">个人设置
        <div class="sectionl" id="setting">
            <div>修改密码</div>
            <div>更改主题
                <ul class="section2">
                    <li>default</li>
                    <li>grey</li>
                    <li>lightblue</li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="body">
    <div class="bigmenus">
        <div class="topmenus-big">个人导航</div>
        <div class="topmenus-big">个人任务</div>
        <div id="nav1" class="secondmenus-big">
             <div>待办事项</div>
            <div>工作计划</div>
            <div>工作任务</div>
            <div>备忘录</div>
        </div>
        <div class="topmenus-big">沟通管理</div>
        <div class="secondmenus-big">
            <div>公司论坛</div>
            <div>邮件管理</div>
            <div>即时聊天</div>
            <div>通讯录</div>
        </div>
        <div class="topmenus-big">行政管理</div>
        <div class="secondmenus-big">
            <div>会议管理</div>
            <div>考勤管理</div>
            <div>KPI考核</div>
            <div>办公用品管理</div>
        </div>
        <div class="topmenus-big topm1">系统设置</div>
        <div class="secondmenus-big">
            <div>用户管理</div>
            <div>权限管理</div>
            <div>岗位管理</div>
        </div>
    </div>
    <div id="main">
        <div class="tabs">
            <div class="tab">首页</div>
            <div class="tab">个人任务&nbsp;<span>×</span></div>
        </div>
        <div id="maincontent">
       <iframe src="${pageContext.request.contextPath }/user/UserServlet?method=list&pageIndex=1&clear=true" style="width: 600px;height: 400px;"></iframe>
        </div>
    </div>
</div>
<div class="foot">
    <div>System OA</div>
    <div class="btmright">&copy;恒远国际</div>
    
</div>
<div id="rightmenu" style="height: 100px; width: 100px; background-color: red;"></div>
</body>
</html>