<%@ page language="java" pageEncoding="UTF-8"%>

<!--  <font color="#FFFFFF">
    当前页:第${pageView.currentpage}页 | 总记录数:${pageView.totalrecord}条 | 每页显示:${pageView.maxresult}条 | 总页数:${pageView.totalpage}页</font>　
<c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
    <c:if test="${pageView.currentpage==wp}"><b><font color="#FFFFFF">第${wp}页</font></b></c:if>
    <c:if test="${pageView.currentpage!=wp}"><a href="javascript:topage('${wp}')" class="a03">第${wp}页</a></c:if>
</c:forEach>-->
<script type="text/javascript">
    	function gotopage(currentpage){
    	var form = document.forms[0];
    	if (form.currentpage == null || form.currentpage == undefined || form.currentpage == '') { 
    		var formname=document.getElementById("formname").value;
			form=document.forms[formname];
		} 
    	var totalpage=${pagebean.totalpage } ;
    		if(currentpage<1 || currentpage!=parseInt(currentpage) || currentpage>totalpage){
    			alert("请输入有效值！！");
    			document.getElementById("pagenum").value = '';
    		}else{
	    		var pagesize = document.getElementById("pagesize").value;
	    		form.currentpage.value=currentpage;
	    		form.pagesize.value=pagesize;
	    		form.submit();
    		}
    	}
    	
    	function changesize(pagesize,oldvalue){
    		var form = document.forms[0];
    		if (form.currentpage == null || form.currentpage == undefined || form.currentpage == '') { 
    			var formname=document.getElementById("formname").value;
				form=document.forms[formname];
			} 
    		if(pagesize<=0 || pagesize!=parseInt(pagesize)){
    			alert("请输入合法值！！");
    			document.getElementById("pagesize").value = oldvalue;
    		}else{
    			form.submit();
    		}
    	}
    </script>
    <input type="hidden"  name="currentpage" value="1">
    共[${pagebean.totalrecord }]条记录,
    每页<input type="text" id="pagesize" name="pagesize" value="${pagebean.pagesize }" onchange="changesize(this.value,${pagebean.pagesize })" style="width: 30px" maxlength="2">条,
    共[${pagebean.totalpage }]页,
    当前[${pagebean.currentpage }]页
    &nbsp;&nbsp;&nbsp;
    <c:if test="${pagebean.currentpage>1 }" >
    <a href="javascript:void(0)" onclick="gotopage(${pagebean.previouspage })">上一页</a>
	 </c:if>   
	    <c:forEach var="pagenum" items="${pagebean.pagebar}">
	    	<c:if test="${pagenum==pagebean.currentpage}">
	    		<font color="red">${pagenum }</font>
	    	</c:if>
	    	
	    	<c:if test="${pagenum!=pagebean.currentpage}">
	    		<a href="javascript:void(0)" onclick="gotopage(${pagenum })">${pagenum }</a>
	    	</c:if>
	    </c:forEach>
    <c:if test="${pagebean.currentpage<pagebean.totalpage }" >
    <a href="javascript:void(0)" onclick="gotopage(${pagebean.nextpage })">下一页</a>
    </c:if>
    <input type="text" id="pagenum" style="width: 30px" />
    <input type="button"  value=" GO "  onclick="gotopage(document.getElementById('pagenum').value)" />