<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@include file="common.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div class="table-responsive">
    <table class="table">
        <thead class="Table cell">
        <td>ID</td>
        <td>姓名</td>
        <td>密码</td>
        <td>number</td>
        </thead>
        <tbody>
        <c:forEach var="user" items="${user}">
            <tr class="success">
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.password}</td>
                <td>${user.number}</td>
            </tr>
        </c:forEach>>
        </tbody>
    </table>
</div>
</body>
</html>
