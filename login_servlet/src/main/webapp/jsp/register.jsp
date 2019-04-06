<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>Register</title>
    <link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="webjars/jquery/3.3.1-2/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="../css/style.css"/>
</head>
<body>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <form name="registerForm" method="POST" action="/sign_up.do">
            <input id="username" type="text" name="login" value="" required max="${name.length}"
                   placeholder="Username"/>
            <input id="password" type="text" name="password" value="" required max="${pass.length}"
                   placeholder="Password"/>
            <input id="role" type="text" name="role" value="" required  placeholder="Role"/>
            <input id="register_button" type="submit" value="Sign Up"/>
        </form>
        <br/>
        <p style="color:red"> ${addUserError}</p>
        <br/>
        <p style="color:red">${userExists}</p>
    </div>
</div>
</body>
</html>