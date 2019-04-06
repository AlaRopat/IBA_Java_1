<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false"%>
<html>
<head>
    <link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="webjars/jquery/3.3.1-2/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="../css/style.css"/>
</head>
<body>
<div id="formContent">
    <form method="post" action="/add.do">
        <label for="addName">
            Enter Name
        </label>
        <input id="addName" type="text" name="add_name" value=""/>
        <label for="addPhone">
            Enter Phone
        </label>
        <input id="addPhone" type="text" name="add_phone" value=""/>
        <label for="addEmail">
            Enter Email
        </label>
        <input id="addEmail" type="text" name="add_email" value=""/>
        <input type="submit" id="add_button" value="Submit"/>
    </form>
</div>
</body>
</html>