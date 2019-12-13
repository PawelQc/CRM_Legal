<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Project report</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Project profitability report for: ${projectReport.project.signature}</h1>
    <table class="table table-hover">
        <tr>
            <th>Total man-hours</th>
            <td>${projectReport.amountOfHours}</td>
        </tr>
        <tr>
            <th>Value of rendered services</th>
            <td>${projectReport.potentialValueOfRenderedServices} PLN</td>
        </tr>
        <tr>
            <th>Cap on remuneration</th>
            <td>${projectReport.project.capOnRemuneration} PLN</td>
        </tr>
        <tr>
            <th>Is project profitable?</th>
            <td>${projectReport.projectIsProfitable}</td>
        </tr>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
