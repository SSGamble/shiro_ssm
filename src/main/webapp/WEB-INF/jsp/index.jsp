<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<%-- 权限配置 --%>
<a href="admin/listUser">权限配置</a><span class="desc">(权限配置，只允许 admin 访问)</span><br><br>

<c:if test="${empty subject.principal}">
    <a href="login">登录</a><br><br>
</c:if>
<c:if test="${!empty subject.principal}">
    <span class="desc">你好，${subject.principal}，</span>
    <a href="doLogout">退出</a><br><br>
</c:if>

<a href="listProduct">查看产品</a><span class="desc">(要有查看产品权限, zhang3有，li4 有)</span><br>
<a href="deleteProduct">删除产品</a><span class="desc">(要有删除产品权限, zhang3有，li4 有)</span><br>
<a href="deleteOrder">删除订单</a><span class="desc">(要有删除订单权限, zhang3有，li4没有)</span><br>

</body>
</html>