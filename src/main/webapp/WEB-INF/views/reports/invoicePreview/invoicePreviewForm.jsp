<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Invoice preview form</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Invoice preview </h1>
    <h4 class="text-center">Choose a client and date to generate an invoice preview (4-week billing period)</h4>
    <p class="text-warning">${errorInvalidData}</p>
    <p class="text-warning">${errorNotSufficientData}</p>

    <form action="/reports/invoice-preview/process" method="post">
        <div class="form-group">
            <label for="clientId">Select client:</label>
            <select name="clientId" class="form-control">
                <c:forEach items="${clients}" var="client">
                    <option value="${client.id}" id="clientId">${client.nameDisplay}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="dateId">Select start date (must be first Monday of a month):</label>
            <input type="date" name="startDate" id="dateId" class="form-control">
        </div>
        <input type="submit" value="Generate report">
    </form>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
