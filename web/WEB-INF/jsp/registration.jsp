<%--
  Created by IntelliJ IDEA.
  User: Tykow
  Date: 15.02.2022
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="email">Email:
        <input type="text" name="email" id="email">
    </label><br>
    <label for="password">Password:
        <input type="password" name="password" id="password">
    </label><br>
    <label for="firstName">First name:
        <input type="text" name="firstName" id="firstName">
    </label><br>
    <label for="lastName">Last name:
        <input type="text" name="lastName" id="lastName">
    </label><br>
    <label for="position"></label>Position:
    <select name="position" id="position">
        <c:forEach var="position" items="${requestScope.position}">
            <option value="${position}">${position}</option>
        </c:forEach>
    </select><br>
    <label for="birthday">Birthday:
        <input type="date" name="birthday" id="birthday">
    </label><br>
    <br>
    <button type="submit">Send</button>
</form>

</body>
</html>
