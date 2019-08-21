<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login success</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<br>
<br>
<br>
<br>

<div class="container">
    <h2 class="text-center">Successful logging as user: ${loggedInUser.nameDisplay}</h2>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
