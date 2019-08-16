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

<h3>Generate report</h3>

<ul>
    <li>
        <a href="/reports/monthly-employee-report/form">monthly employee report</a>
    </li>
    <li>
        <a href="/reports/project-report/form">project profitability report</a>
    </li>
</ul>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
