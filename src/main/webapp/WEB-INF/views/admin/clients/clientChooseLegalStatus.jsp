<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>

<%@ include file="/fragments/header.jspf" %>

<h4>Adding new client - please choose client's legal status:</h4>
<ul>
    <li>
        <a href="/clients/legal-person/add">legal person</a>
    </li>
    <li>
        <a href="/clients/natural-person/add">natural person</a>
    </li>
</ul>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
