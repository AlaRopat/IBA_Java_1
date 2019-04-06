<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="webjars/jquery/3.3.1-2/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="../css/style.css"/>
</head>
<body>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <form name="LoginForm" method="POST" action="/authentication.do">
            <input type="text" id="login" class="fadeIn second" name="login" placeholder="login">
            <input type="text" id="password" class="fadeIn third" name="password" placeholder="password">
            <input type="submit" class="fadeIn fourth" value="Log In">
            <br/>
            <p style="color:red">${errorLoginPassMessage}</p>
            <br/>
            <p style="color:red">${wrongAction}</p>
            <br/>
            <p style="color:red">${nullPage}</p>
            <br/>
        </form>

        <div id="formFooter">
            <a class="underlineHover" href="/register.do">Register</a>
        </div>
    </div>
</div>
</body>
</html>
