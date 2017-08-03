<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义列表</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$("#grid").datagrid({
			striped : true,
			rownumbers : true,
			singleSelect : true,
			fitColumns : true,
			toolbar : [
				{
					id : 'deploy',
					text : '发布新流程',
					iconCls : 'icon-add',
					handler : function(){
						location.href = "${pageContext.request.contextPath}/page_workflow_processdefinition_deploy.action";
					}
				}
			]
		});
	});
	function del(id){
		$.messager.confirm("确认信息","你确认删除当前流程定义吗?",function(r){
			if(r){
				//发送请求删除数据
				var url="${pageContext.request.contextPath}/processDefinitionAction_delete.action?id="+id;
				$.post(url,{},function(data){
					if(data=='0'){
						window.location.reload(true);
					}else{
						$.messager.alert("提示信息","删除失败,当前流程定义正在被使用!","warning");
					}
				});
			}
		});
	}
</script>	
</head>
<body class="easyui-layout">
  <div region="center" >
  	<table id="grid" class="easyui-datagrid">  
  		<thead>
  			<tr>
  				<th data-options="field:'id'" width="120">流程编号</th>
  				<th data-options="field:'name'" width="200">流程名称</th>
  				<th data-options="field:'key'" width="100">流程key</th>
  				<th data-options="field:'version'" width="80">版本号</th>
  				<th data-options="field:'viewpng'" width="200">查看流程图</th>
  			</tr>
  		</thead>
  		<tbody>
  				<s:iterator value="list" var="processDefinition">
  					<!-- 在循环过程中 ，将  processDefinition 对象，同时放入 root 和 map 中-->
  				<tr>
  					<td>
  						<s:property value="id"/> <!-- 从root找 -->
  			   <%-- <s:property value="#processDefinition.id"/>  从map找 --%>
  					</td>
  					<td><s:property value="name"/></td>
  					<td><s:property value="key"/></td>
  					<td><s:property value="version"/></td>
  					<td>
  						<a onclick="window.showModalDialog('processDefinitionAction_showpng?id=${id} ')"  Class="easyui-linkbutton" data-options="iconCls:'icon-search'">查看流程图
  						</a>
  						<a onclick="del('${id}') "  Class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除
  						</a>
  					</td>
  				</tr>
  				</s:iterator>
  		</tbody>
  	</table>
  </div>
</body>
</html>