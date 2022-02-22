<%--
  Created by IntelliJ IDEA.
  User: Tykow
  Date: 08.02.2022
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%@include file="header.jsp" %>
        <div>
           <span>Content. Русский</span>
            <p>Size: ${requestScope.users.size()}
            </p>
        </div>
    <%@include file="footer.jsp" %>
</body>
</html>
