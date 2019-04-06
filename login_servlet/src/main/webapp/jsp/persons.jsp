<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false"%>
<html>
<head>
    <link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="webjars/jquery/3.3.1-2/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="../css/style.css"/>
</head>
<body>
<table border="1" cellpadding="5" width="600">
    <tr>
        <th>Name</th>
        <th>Phone</th>
        <th>Email</th>
    </tr>
    <c:forEach var="person" items="${personList}">
        <tr>
            <td>${person.name}</td>
            <td>${person.phone}</td>
            <td>${person.email}</td>
        </tr>

    </c:forEach>
</table>
<p><font color="red">${errorMessage}</font></p>
</body>
</html>