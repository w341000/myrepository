<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>修改订单的支付方式</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<script language="JavaScript">
function checkfm(form){
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/order/manage_modifyPaymentWay" method="post" onsubmit="return checkfm(this)">
<input type="hidden" name="orderid" value="${orderid}" />
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td > <font color="#FFFFFF">修改订单的支付方式：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td><input type="radio"  name="paymentWay" value="NET" <c:if test="${order.paymentWay=='NET'}" >checked="checked"</c:if>/>网上支付</td>
    </tr>
    <c:if test="${order.orderDeliverInfo.deliverWay!='GENERALPOST' && order.orderDeliverInfo.deliverWay!='EMS' }">
    <tr bgcolor="f5f5f5"> 
      <td><input type="radio"  name="paymentWay" value="COD" <c:if test="${order.paymentWay=='COD'}" >checked="checked"</c:if>/>货到付款</td>
    </tr></c:if>
    <tr bgcolor="f5f5f5"> 
      <td><input type="radio"  name="paymentWay" value="BANKREMITTANCE" <c:if test="${order.paymentWay=='BANKREMITTANCE'}" >checked="checked"</c:if>/>银行电汇</td>
    </tr>
     <tr bgcolor="f5f5f5"> 
      <td><input type="radio"  name="paymentWay" value="POSTOFFICEREMITTANCE" <c:if test="${order.paymentWay=='POSTOFFICEREMITTANCE'}" >checked="checked"</c:if>/>邮局汇款</td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="submit" name="SYS_SET" value=" 确 定 " class="frm_btn">
        </div></td>
    </tr>
  </table>
</form>
<br>
</body>
</html>