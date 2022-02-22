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
<img src="/images/users/Ulit_work.png" sizes="200 * 200" alt="User image">
<form action="${pageContext.request.contextPath}/registration" method="post" enctype="multipart/form-data">
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
    <label for="imageId">Image:
        <input type="file" name="image" id="imageId" required>
    </label><br>
    <%--    <label for="position"></label>Position:--%>
    <%--    <select name="position" id="position">--%>
    <%--        <c:forEach var="position" items="${requestScope.position}">--%>
    <%--            <option value="${position}">${position}</option>--%>
    <%--        </c:forEach>--%>
    <%--    </select><br>--%>

    <label for="title" id="title"></label>Position:
    <c:forEach var="title" items="${requestScope.title}">
        <input type="radio" name="title" value="${title}">${title}
        <%--            <option value="${title}">${title}</option>--%>
    </c:forEach>
    <br>
    <label for="birthday">Birthday:
        <input type="date" name="birthday" id="birthday" required>
    </label><br>
    <br>
    <button type="submit">Send</button>
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span>
                <br>
            </c:forEach>
        </div>
    </c:if>
</form>

</body>
</html>
