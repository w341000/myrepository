<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>出错啦!!</title>
  </head>
  
  <body>
   <font color="red" size="20"> 对不起出错啦<br></font>
   <font color="red" size="20"> 对不起出错啦<br></font>
   <font color="red" size="20"> 对不起出错啦<br></font>
   <font color="red" size="20"> 对不起出错啦<br></font>
   <font color="red" size="20"> 对不起出错啦<br></font>
   <font color="red" size="20"> 对不起出错啦<br></font>
   <s:fielderror/>
  </body>
</html>
