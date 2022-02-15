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
<form action="/registration" method="post">
    <label for="email">Email:
        <input type="text" name="email" id="email">
    </label><br>
    <label for="password">Password:
        <input type="password" name="password" id="password">
    </label><br>
    <label for="firstname">First name:
        <input type="text" name="firstname" id="firstname">
    </label><br>
    <label for="lastname">Last name:
        <input type="text" name="lastname" id="lastname">
    </label><br>
    <label for="role"></label>Role:
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option value="${role}">${role}</option>
        </c:forEach>
    </select><br>
    <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" value="${gender}">${gender}
        <br>
    </c:forEach>
    <br>
    <button type="submit">Send</button>
</form>

</body>
</html>
