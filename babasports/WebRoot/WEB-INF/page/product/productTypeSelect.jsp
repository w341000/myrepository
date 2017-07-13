<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title> 类别选择 </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<script type="text/javascript" src="/js/FoshanRen.js"></script>
<script type="text/javascript" src="/js/xmlhttp.js"></script>

<script type="text/javascript">
function checkIt(){
	var objForm = document.forms[0];
	var form = opener.document.forms[0];
	if (form){
		form.typeid.value = objForm.dicId.value;
		form.v_type_name.value = objForm.dicName.value;
	}
	window.close();
}
function getDicName(dicId,strDicName){
	var objForm = document.forms[0];
	objForm.dicId.value = dicId;
	objForm.dicName.value = strDicName;
}
function getTypeList(typeid){
		var typecontent = document.getElementById('typecontent');
		if(typecontent){
			typecontent.innerHTML= "数据正在加载...";
			send_request(function(value){typecontent.innerHTML=value}, '/control/product/type/manage_gettypelist?typeid='+ typeid, true);
		}
}
</script>
<style>
<!--
.inputText{
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #666666;
	border: 1px solid #999999;
}
body {
	font-family: Georgia, "Times New Roman", Times, serif;
	font-size: 12px;
	color: #666666;
}
-->
</style>
</head>

<body>

产品类别列表,请选择分类:<br>
导航:<a href='/control/product/manage_selectUI'>顶级目录</a>
<c:forEach var="menu" items="${menutypes }">
	&gt;&gt;<a href='/control/product/manage_selectUI?typeid=${menu.id}'>${menu.name}</a>
</c:forEach>
<form method="post" name="main" action="">
<table width="100%" border="0" cellspacing="1" cellpadding="1">
  <input type="hidden" name="dicId" />
  <input type="hidden" name="dicName" />
  
	<tr><td id="typecontent">
	<table width="100%" border="0">
	<tr>
	<c:forEach items="${types}" var="type" varStatus="loop" >		
	    <td>
		<c:if test="${fn:length(type.children)>0}">
		<a href="/control/product/manage_selectUI?typeid=${type.id}"><b>${type.name}</b></a></c:if>
		<c:if test="${fn:length(type.children)==0}"> <INPUT TYPE="radio" NAME="type" onclick="getDicName('${type.id}','${type.name}')">${type.name}</c:if>
		</td>
		<c:if test="${loop.count%5==0}"></tr><tr></c:if>
	</c:forEach>
	</tr></table>
	</td></tr>
	<tr><td colspan="2" align="center">
		<input type='button' name='create' value=" 确 认 " onClick="javascript:checkIt()">
		<input type='button' name="cancel" onClick="javaScript:window.close()" value=" 取 消 "> 
    </td></tr>
</table>
</form>
</body>
</html>