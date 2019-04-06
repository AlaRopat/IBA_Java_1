<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Success Page</title>
    <link type="text/css" rel="stylesheet" href="../css/style.css"/>
</head>
<body>
<div class="wrapper fadeInDown">
    <div id="formContent">
        ${successRegister}
        <br>
        ${successAddPerson}
    </div>
</div>
</body>
</html>