<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>

	<c:forEach items="${products }" var="product" varStatus="statu">
		<li class="bj_blue"><a href="/product/view?productid=${product.id}" target="_blank" title="${product.name}">${product.name}</a></li></c:forEach>	
		
		

		 
	