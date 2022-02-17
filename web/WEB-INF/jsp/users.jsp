<%--
  Created by IntelliJ IDEA.
  User: Tykow
  Date: 15.02.2022
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h1>Selected user</h1>
<ul>
    <c:forEach var="user" items="${requestScope.users}">
        <li>${fn:toLowerCase(user.description)}</li>
    </c:forEach>
</ul>

</body>
</html>
