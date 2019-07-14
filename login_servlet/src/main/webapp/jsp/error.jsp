<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" isErrorPage="true" %>
<html>
<head>
    <title>Error Page</title>
    <link type="text/css" rel="stylesheet" href="../css/style.css"/>
</head>
<body>
<div class="wrapper fadeInDown">
    <div id="formContent">
        Exception: ${error_message}
    </div>
</div>
</body>
</html>