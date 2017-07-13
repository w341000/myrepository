<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<title>产品类别管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="/css/vip.css" type="text/css" />
<script type="text/javascript">
<!--
	//到指定的分页页面
	function topage(page){
		var form = document.forms[0];
		form.page.value=page;
		form.submit();
	}
//-->
</script>
<script type="text/javascript" src="/js/FoshanRen.js"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
<form action="/control/product/type/list" method="get" >

<input type="hidden" name="parentid" value="${parentid }"/>
<input type="hidden" name="name" value="${name }"/>
<input type="hidden"  name="query" value="${query }"/>
  <table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
    <tr ><td colspan="6"  bgcolor="6f8ac4" align="right">
    	<%@ include file="/WEB-INF/page/share/fenye.jsp" %>
   </td></tr>
    <tr>
      <td width="8%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">代号</font></div></td>
      <td width="5%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">修改</font></div></td>
      <td width="20%" bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">产品类别名称</font></div></td>
	  <td width="10%" nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">创建下级类别</font></div></td>
	  <td width="15%" bgcolor="6f8ac4"><div align="center"><font color="#FFFFFF">所属父类</font></div></td>
	  <td nowrap bgcolor="6f8ac4"> <div align="center"><font color="#FFFFFF">备注</font></div></td>
    </tr>
<!---------------------------LOOP START------------------------------>
<c:forEach items="${pagebean.list}" var="productType">
    <tr>
      <td bgcolor="f5f5f5"> <div align="center">${productType.id }</div></td>
      <td bgcolor="f5f5f5"> <div align="center"><a href="/control/product/type/manage_editUI?id=${productType.id }">
	  <img src="/images/edit.gif" width="15" height="16" border="0" /></a></div></td>
      <td bgcolor="f5f5f5"> <div align="center"><a href='/control/product/type/list?parentid=${productType.id }'>${productType.name }</a> <c:if test="${fn:length(productType.children)>0}"><font color=red>(有${fn:length(productType.children)}个子类)</font></c:if></div></td>
	  <td bgcolor="f5f5f5"><div align="center"><a href="/control/product/type/manage_addUI?parentid=${productType.id}">创建子类别</a></div></td>
	  <td bgcolor="f5f5f5" align="center"><c:if test="${!empty productType.parent}">${productType.parent.name}</c:if></td>
	  <td bgcolor="f5f5f5">${productType.note }</td>
	</tr>
</c:forEach>
    <!----------------------LOOP END------------------------------->
    <tr> 
      <td bgcolor="f5f5f5" colspan="6" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="3">
          <tr> 
            <td width="5%"></td>
              <td width="85%">
              <input name="AddDic" type="button" class="frm_btn" id="AddDic" onclick="javascript:window.location.href='/control/product/type/manage_addUI?parentid=${param.parentid}'" value="添加类别" /> &nbsp;&nbsp;
			  <input name="query" type="button" class="frm_btn" id="query" onclick="javascript:window.location.href='/control/product/type/manage_queryUI'" value=" 查 询 " /> &nbsp;&nbsp;
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</form>
</body>
</html>