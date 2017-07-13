<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>测试测试</title>
</head>

<body>
	
	<s:iterator  value="brands" var="brand">
	<s:url id="Url_first" includeParams="get"> <s:param name="brandid" value="brand.code" /></s:url>
	<s:if test='#parameters.brandid[0]==brand.code' ><strong><em><s:property value="brand.name"/></em></strong></s:if>
	<s:else><s:a href="%{Url_first}" title="%{brand.name}"><s:property value="brand.name"/></s:a></s:else>
	</s:iterator>
	
	<s:radio list="#{'MAN':'男','WOMEN':'女'}" name="gender"></s:radio>
	
	<c:forEach items="${brands}" var="brand">
				<li><a href="/product/list?sort=${param.sort}&typeid=${param.typeid}&brandid=${brand.code}&sex=${param.sex}">${brand.name}</a></li>
	</c:forEach>
</body>
</html>
