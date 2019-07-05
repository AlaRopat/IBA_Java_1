<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false"%>
<html>
<head>
    <link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="webjars/jquery/3.3.1-2/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="../css/style.css"/>
</head>
<body>

<h1> Welcome, ${user.login}</h1>


<div id="formFooter">
    <a class="underlineHover" href="/persons.do">Show Persons List</a>
    <br/>
    <a class="underlineHover" href="/go_to_person.do">Add Person</a>
</div>


</body>
</html>