<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>部门显示</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js" ></SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<form action="/control/department/list" method="get">
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr ><td colspan="4" bgcolor="6f8ac4" align="right">
    	<%@ include file="/WEB-INF/page/share/fenye.jsp" %>
   </td></tr>
    <tr>
      <td width="30%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">代号</font></div></td>
      <td width="8%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">修改</font></div></td>
      <td bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">名称</font></div></td>
      <td width="10%" bgcolor="6f8ac4"></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<c:forEach items="${pagebean.list}" var="entry">
    <tr>
      <td bgcolor="f5f5f5"> <div align="center">${entry.departmentid}</div></td>
      <td bgcolor="f5f5f5"> <div align="center">
      <security:permission  module="department" privilege="update">
      <a href="/control/department/manage_editDepartmentUI?departmentid=${entry.departmentid}">
	  <img src="/images/edit.gif" width="15" height="16" border="0"></a>
	</security:permission>
	  </div></td>
      <td bgcolor="f5f5f5"> <div align="center">${entry.name}</div></td>
      <td bgcolor="f5f5f5"> <div align="center">
        <security:permission module="department" privilege="delete">
         <a href="/control/department/manage_delete?departmentid=${entry.departmentid}">删除</a>
       </security:permission>
      </div></td>
	</tr>
</c:forEach>
    <!----------------------LOOP END------------------------------->
    <tr> 
      <td bgcolor="f5f5f5" colspan="4" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="5%"></td>
              <td width="85%">
              <security:permission module="department" privilege="insert">
              <input type="button" class="frm_btn" onClick="javascript:window.location.href='/control/department/manage_addDepartmentUI'" value="添加部门"> &nbsp;&nbsp;
           	  </security:permission>
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</form>
</body>
</html>