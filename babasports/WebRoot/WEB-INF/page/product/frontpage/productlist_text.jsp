<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>户外用品 巴巴运动网</title>    
	<link href="/css/global/header01.css" rel="stylesheet" type="text/css">
	<link href="/css/product/list.css" rel="stylesheet" type="text/css" />	
	<link href="/css/global/topsell.css" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="Keywords" content="户外用品">
	<META name="description" content="中国最大的户外用品网上商城,致力于提供最高品质，时尚的户外用品，让所有热爱生活的人们，能够享受到户外所带来的轻松、惬意和乐趣。">
<script type="text/javascript" src="/js/xmlhttp.js"></script>
<script type="text/javascript">
<!--
	function getTopSell(typeid){
		var salespromotion = document.getElementById('salespromotion');		
		if(salespromotion && typeid!=""){
			salespromotion.innerHTML= "数据正在加载...";
			send_request(function(value){salespromotion.innerHTML=value},
					 '/product/switch_topsell?typeid='+ typeid, true);
		}
	}
	function getViewHistory(){
		var viewHistoryUI = document.getElementById('viewHistory');		
		if(viewHistoryUI){
			viewHistoryUI.innerHTML= "数据正在加载...";
			send_request(function(value){viewHistoryUI.innerHTML=value},
					 '/product/switch_getViewHistory', true);
		}
	}
	function pageInit(){
		getTopSell("${type.id}");
		getViewHistory();
	}
//-->
</script>
</head>

<body class="ProducTypeHome2" onload="javascript:pageInit()">
	<jsp:include page="/WEB-INF/page/share/Head.jsp"/>
	
    <div id="position">您现在的位置: <a href="/product/list" name="linkHome">巴巴运动网</a> 
    <c:forEach var="menu" items="${types }" varStatus="status">
	&gt;&gt;<c:if test="${ !status.last}"><a href='/product/list?typeid=${menu.id}'>${menu.name}</a> </c:if>
	<c:if test="${ status.last}">${menu.name}</c:if>
	</c:forEach>（${pagebean.totalrecord}个）
	</div>

    <!--页面左侧分类浏览部分-->
    <div class="browse_left">
         <div class="browse">
            <div class="browse_t">${type.name}</div>
			
				<h2><span class="gray">浏览下级分类</span></h2>
				<ul><c:forEach items="${subtypes}" var="childtype">						
				<li class='bj_blue'><a href="/product/list?typeid=${childtype.id}">${childtype.name}</a></li></c:forEach>		    
			</ul>
	     </div>
<DIV id="sy_biankuang">
        <DIV class="lanmu_font">最畅销${type.name}</DIV>
        <DIV style="PADDING-LEFT: 10px; COLOR: #333333" id="salespromotion">
			
        </DIV>
</DIV>
		 <br/>
		 <div class="browse">
	          <div class="browse_t">您浏览过的商品</div>
			  <ul id="viewHistory"></ul>
	     </div>
    </div>
    <!--页面右侧分类列表部分开始-->
    <div class="browse_right">
         <div class="select_reorder">
              <div class="reorder_l">请选择排序方式： 
              <s:iterator  value="#{'selldesc':'销量多到少','sellpricedesc':'价格高到低','sellpriceasc':'价格低到高','':'最近上架时间'}">
				<s:url id="Url_first"  includeParams="get"  >  <s:param name="currentpage" value="1" /><s:param name="sort" value="key" /></s:url>
				<s:if test='#parameters.sort[0]==key' ><strong><em><s:property value="value"/></em></strong></s:if>
				<s:elseif test="#parameters.sort==null && key==''"><strong><em><s:property value="value"/></em></strong></s:elseif>
				<s:else><s:a href="%{Url_first}" title="%{value}"><s:property value="value"/></s:a></s:else>
				</s:iterator> </div>
              
		      <div class="reorder_r">显示方式：
		      <s:iterator  value="#{'imagetext':'图文版','':'图片版'}">
				<s:url id="Url_first" includeParams="get"> <s:param name="style" value="key" /></s:url>
				<s:if test='#parameters.style[0]==key' ><strong><em><s:property value="value"/></em></strong></s:if>
				<s:elseif test="#parameters.style==null && key==''"><strong><em><s:property value="value"/></em></strong></s:elseif>
				<s:else><s:a href="%{Url_first}" title="%{value}"><s:property value="value"/></s:a></s:else>
				</s:iterator>
		      </div>
			<div class="emptybox"></div>
			 <div class="brand">
				<div class="FindByHint">按<strong>品牌</strong>选择：</div>
				<ul class="CategoryListTableLevel1">
					<s:iterator  value="#request.brands">
						<li><s:url id="Url_first" includeParams="get"> <s:param name="brandid" value="code" /></s:url>
						<s:if test='#parameters.brandid[0]==code' ><strong><em><s:property value="name"/></em></strong></s:if>
						<s:else><s:a href="%{Url_first}" title="%{name}"><s:property value="name"/></s:a></s:else></li>
						</s:iterator>
				</ul>
			 </div>
			 <div class="SubCategoryBox">
				<div class="FindByHint">按<strong>男女款</strong>选择：</div>
				<ul class="CategoryListTableLevel1">
				<s:iterator  value="#{'MAN':'男款','WOMEN':'女款','NONE':'男女均可','':'全部'}">
				<li><s:url id="Url_first" includeParams="get">  <s:param name="currentpage" value="1" /><s:param name="sex" value="key" /></s:url>
					<s:if test='#parameters.sex[0]==key' ><strong><em><s:property value="value"/></em></strong></s:if>
					<s:elseif test="#parameters.sex==null && key==''"><strong><em><s:property value="value"/></em></strong></s:elseif>
					<s:else><s:a href="%{Url_first}" title="%{value}"><s:property value="value"/></s:a></s:else>
				</li></s:iterator>
				</ul>
			 </div>
		</div>
	     <div id="divNaviTop" class="number">
	          <div class="number_l">以下<span class='number_white'>${pagebean.totalrecord}</span>条结果按<span class="number_white">
				<c:choose>
				  <c:when test="${'selldesc'==param.sort}">销量多到少</c:when>
				  <c:when test="${'sellpricedesc'==param.sort}">价格高到低</c:when>
				  <c:when test="${'sellpriceasc'==param.sort}">价格低到高</c:when>
				  <c:otherwise>最近上架时间</c:otherwise>
				</c:choose>
			  </span>排列，显示方式是<span class="number_white"><c:if test="${param.style=='imagetext'}">图文版</c:if><c:if test="${param.style!='imagetext'}">图片版</c:if></span>　每页显示<span class="number_white">${pagebean.pagesize}</span>条</div>
		      <div class="turnpage">
                <div><em>第${pagebean.currentpage}页</em></div>
		      </div>
	     </div>
<!---------------------------LOOP START------------------------------>
<c:forEach items="${pagebean.list}" var="entry">	
		<div class="goodslist">
          <div class="goods" style="cursor:hand;background:url(<c:forEach items="${entry.styles}" var="pic">${pic.image140FullPath}</c:forEach>) center center no-repeat"><a href="/html/product/${entry.type.id }/${entry.id}.shtml" target="_blank">
            <img src="/images/global/product_blank.gif" alt="${entry.name}" width="140" height="168"  border="0"/></a></div>
          <div class="goods_right">
                <h2><a href="/html/product/${entry.type.id }/${entry.id}.shtml" target="_blank" title="${entry.name}">${entry.name}</a></h2>
	           <div class="message"><ul>
			  <c:if test="${!empty entry.brand}"> <li>品牌：${entry.brand.name}</li></c:if>
			   </ul></div>
	           <div class="content">&nbsp;&nbsp;&nbsp;<c:out value="${fn:substring(entry.description,0,200)}"/></div>
	           <div class="message_bottom">
	                <div class="save"><s>￥${entry.marketprice}</s>　<strong><em>￥${entry.sellprice}</em></strong>　节省：${entry.savedPrice}</div>
			        <div class="buy"><a href="/html/product/${entry.type.id }/${entry.id}.shtml"><img src='/images/sale.gif' width='84' height='24' border='0' /></a></div>
	           </div>
          </div>
          <div class="empty_box"></div>
        </div>
</c:forEach>
<!---------------------LOOP END------------------------------->	
	    	<%@ include file="/WEB-INF/page/share/fenye.jsp" %>
    </div>
	<jsp:include page="/WEB-INF/page/share/Foot.jsp" />
</body>
</html>