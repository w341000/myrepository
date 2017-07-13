<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<html>
<head>
<title>修改产品</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<script type="text/javascript" src="/js/FoshanRen.js"></script>
<script type="text/javascript" src="/js/jscripts/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript">
tinyMCE.init({
	language : "zh_cn",
	mode : "textareas",
	theme : "advanced",
	//width : "500",
	plugins : "table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,zoom,flash,searchreplace,print,contextmenu",
	theme_advanced_buttons1_add_before : "save,separator",
	theme_advanced_buttons1_add : "fontselect,fontsizeselect",
	theme_advanced_buttons2_add : "separator,insertdate,inserttime,preview,zoom,separator,forecolor,backcolor",
	theme_advanced_buttons2_add_before: "cut,copy,paste,separator,search,replace,separator",
	theme_advanced_buttons3_add_before : "tablecontrols,separator",
	theme_advanced_buttons3_add : "emotions,iespell,flash,advhr,separator,print",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_path_location : "bottom",
	plugin_insertdate_dateFormat : "%Y-%m-%d",
	plugin_insertdate_timeFormat : "%H:%M:%S",
	extended_valid_elements : "a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]",
	external_link_list_url : "example_data/example_link_list.js",
	external_image_list_url : "example_data/example_image_list.js",
	flash_external_list_url : "example_data/example_flash_list.js"
});

function Formfield(name, label){
	this.name=name;
	this.label=label;
}
function verifyForm(objForm){
	tinyMCE.triggerSave();//手动把iframe的值赋给textarea表单元素
	var list  = new Array(new Formfield("name", "产品名称"),new Formfield("typeid", "产品类型"),
	new Formfield("baseprice", "产品底价"),new Formfield("marketprice", "产品市场价")
	,new Formfield("sellprice", "产品销售价"),new Formfield("description", "产品描述"));
	for(var i=0;i<list.length;i++){
		var objfield = eval("objForm."+ list[i].name);
		if(trim(objfield.value)==""){
			alert(list[i].label+ "不能为空");
			if(objfield.focus()) objfield.focus();
			return false;
		}
	}
    return true;
}
function SureSubmit(objForm){
	if(verifyForm(objForm)) objForm.submit();
} 
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/product/manage_edit" enctype="multipart/form-data" method="post">
<input type="hidden" name="typeid" value="${product.type.id }">
<input type="hidden" name="id" value="${product.id }">
  <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"> 
      <td colspan="2" ><font color="#FFFFFF">修改产品：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品名称  ：</div></td>
      <td width="75%"> <input type="text" name="name" value="${product.name }" size="50" maxlength="40"  /><font color="#FF0000">*</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品类别<font color="#FF0000">*</font>  ：</div></td>
      <td width="75%"> <input type="text" name="v_type_name" disabled="true" size="30" value="${product.type.name}"/> 
        <input type="button" name="select" value="选择..." onClick="javaScript:winOpen('/control/product/manage_selectUI','列表',600,400)">(<a href="/control/product/type/manage_addUI">添加产品类别</a>)
      </td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">底(采购)价 ：</div></td>
      <td width="75%"> <input type="text" name="baseprice" value="${product.baseprice }" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>元 <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">市场价 ：</div></td>
      <td width="75%"> <input type="text" name="marketprice" value="${product.marketprice }" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>元 <font color="#FF0000">*</font></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">销售价 ：</div></td>
      <td width="75%"> <input type="text" name="sellprice" value="${product.sellprice }" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>元 <font color="#FF0000">*</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">货号 ：</div></td>
      <td width="75%"> <input type="text" name="code" value="${product.code }" size="20" maxlength="30" />(注:供货商提供的便于产品查找的编号)</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">品牌 ：</div></td>
      <td width="75%"> <s:select list="#request.brands" name="brand.code" value="#request.product.brand.code" listKey="code" listValue="name"  headerKey="" headerValue="---无---"></s:select>
        (<a href="/control/brand/manage_addUI">添加品牌</a>)</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">适用性别 ：</div></td>
      <td width="75%"><s:select name="sex" list="#{'NONE':'男女不限','MAN':'男','WOMEN':'女'}" value="#request.product.sexrequest.strName" listKey="key" listValue="value"></s:select>
		</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">型号 ：</div></td>
      <td width="75%"> <input type="text" name="model" value="${product.model }" size="35" maxlength="30" /></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">重量 ：</div></td>
      <td width="75%"> <input type="text" name="weight" value="${product.weight }" size="10" maxlength="10"  onkeypress="javascript:InputIntNumberCheck()"/>克</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">购买说明 ：</div></td>
      <td width="75%"> <input type="text" name="buyexplain" value="${product.buyexplain }" size="35" maxlength="30"  /></td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%" valign="top"> <div align="right">产品简介<font color="#FF0000">*</font> ：</div></td>
      <td width="75%"><textarea  name="description"   cols="80" rows="23" >${product.description }</textarea></td>
	</tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="button" name="edit" value=" 确 认 " class="frm_btn" onClick="javascript:SureSubmit(this.form)">
          &nbsp;&nbsp;<input type="button" name="Button" value=" 返 回 " class="frm_btn" onclick="javascript:history.back()">
        </div></td>
    </tr>
  </table>
</form>
<br>
</body>
</html>