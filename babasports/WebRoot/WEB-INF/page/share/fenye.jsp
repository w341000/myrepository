<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <%@ include file="/WEB-INF/page/share/taglib.jsp" %>


<s:if test="#request.pagebean.currentpage!=1">
						<s:url id="Url_first" includeParams="get">
	                        <s:param name="currentpage" value="%{currentpage-1}" />
                        </s:url>
	<s:a href="%{Url_first}">
		上一页
	</s:a>
</s:if>
<s:iterator value="#request.pagebean.pagebar" id="pagenum">
						<s:url id="Url_first" includeParams="get">
                        <s:param name="currentpage" value="%{pagenum}" />
                        </s:url>
<s:if test="#pagenum==#request.pagebean.currentpage"><font color="red"><s:property/></font></s:if>
<s:else>
	 <s:a href="%{Url_first}">
		<s:property />
	</s:a>
</s:else>
</s:iterator>
<s:if test="#request.pagebean.currentpage<#request.pagebean.totalpage">
						<s:url id="Url_first" includeParams="get">
	                        <s:param name="currentpage" value="%{currentpage+1}" />
	                        <s:param name="param1" value="%{param1}"/>
                        </s:url>
	<s:a href="%{Url_first}">
		下一页
	</s:a>
</s:if>

共[${pagebean.totalrecord }]条记录,
    每页<s:select list="{'1','5','10','20'} " id="pageSizeSelector" value="#request.pagebean.pagesize" onchange="changesize()"></s:select> 条,
    共[${pagebean.totalpage }]页,
    当前[${pagebean.currentpage }]页
    &nbsp;&nbsp;&nbsp;
			
   <script type="text/javascript">
/**
 *
 修改指定URL的参数值
 
 *@param {destiny} URL地址
 *@param {par} 需要修改的参数名
 *@param {destiny} 修改后的参数值
 *@return 修改后的URL地址
 */
	function changeURLPar(destiny, par, par_value)   
	{   
		var pattern = par+'=([^&]*)';   
		var replaceText = par+'='+par_value; 
		if (destiny.match(pattern))   
		{   
			var tmp = '/'+par+'=[^&]*/g';   
			tmp = destiny.replace(eval(tmp), replaceText);   
			return (tmp);   
		}   
		else   
		{   
			if (destiny.match('[\?]'))   
				return destiny+'&'+replaceText;   
			else   
				return destiny+'?'+replaceText;   
		}   
	}   
	function changesize(){
				var url = location.search; 
				var val=document.getElementById("pageSizeSelector").value;
				var curl=changeURLPar(window.location.href,'pagesize',val);
				window.location.href=curl;
    	}
</script>


