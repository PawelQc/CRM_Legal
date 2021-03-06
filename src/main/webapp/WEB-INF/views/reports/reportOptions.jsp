<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Report choice</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Choose report</h1>
    <br>
    <div class="text-center">
        <a href="<c:url value="/reports/monthly-employee-report/form"/>"
           class="btn btn-success rounded-0 text-light m-1 btn-report-option">
            <h4>monthly employee report</h4>
        </a>
        <br><br>
        <a href="<c:url value="/reports/project-report/form"/>" class="btn btn-info rounded-0 text-light m-1 btn-report-option">
            <h4>project profitability report</h4>
        </a>
        <br><br>
        <a href="<c:url value="/reports/invoice-preview/form"/>" class="btn btn-warning rounded-0 text-light m-1 btn-report-option">
            <h4>generate invoice preview</h4>
        </a>
        <br><br>
        <a href="<c:url value="/reports/export-timesheets/form"/>" class="btn btn-success rounded-0 text-light m-1 btn-report-option">
            <h4>export timesheets to excel</h4>
        </a>
    </div>
</div>
<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
