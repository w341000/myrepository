<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/page/share/taglib.jsp" %>
<div id="Head">
  <div id="HeadTop">
    <div id="Logo"><a href="/" target=_top><img alt="中国最大、最安全的户外用品、体育用品网上交易平台！" src="/images/global/logo.gif" border=0 /></a> </div>
    <div id="HeadNavBar">
      <ul>
        <li class="NoSep"><a id="MyBuyCar"  href="/shopping/cart" ><font color="blue"><Strong>购物车</Strong></font></a> </li>
        <li><a href="/user/reg_regUI" >新用户注册</a> </li>
        <li><a href="/user/logon" >用户登录</a> </li>
        <c:if test="${!empty user}"> <li><a href="/user/logout" >退出登录</a> </li></c:if>
        <li><a href="">帮助中心</a> </li>
        <li class="phone">服务热线：010-64663070</li>
      </ul>
    </div>
  </div>
</div>
<!-- Head End -->