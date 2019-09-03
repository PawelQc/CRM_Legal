<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h4 class="text-center text-warning">Application error: database connection limit has been reached. Please try again later (wait approx. 1 hour).</h4>
</div>
<%@ include file="/fragments/footer.jspf" %>
</body>
</html>