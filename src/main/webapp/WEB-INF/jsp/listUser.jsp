<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<%-- 管理菜单 --%>
<%@include file="include/menu.jsp" %>

<%-- 用户信息列表 --%>
<table border="1" cellspacing="0">
    <tr>
        <td>id</td>
        <td>用户名称</td>
        <td>用户密码</td>
        <td>加密盐</td>
        <td>角色</td>
        <td>编辑</td>
        <td>删除</td>
    </tr>
    <c:forEach items="${listUser}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.password}</td>
            <td>${u.salt}</td>
            <%-- 角色 List --%>
            <td>
                <c:forEach items="${user_roles[u]}" var="r">
                    ${r.name} <br>
                </c:forEach>
            </td>
            <td><a href="editUser?id=${u.id}">编辑</a></td>
            <td><a href="deleteUser?id=${u.id}">删除</a></td>
        </tr>
    </c:forEach>
</table>

<%-- 添加用户 --%>
<form action="addUser" method="post">
    用户名: <input type="text" name="name"> <br>
    密码: <input type="password" name="password"> <br><br>
    <input type="submit" value="增加">
</form>

</body>
</html>