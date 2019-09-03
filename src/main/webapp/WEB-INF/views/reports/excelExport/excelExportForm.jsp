<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Employee report form</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Export employee timesheets to excel file</h1>
    <h4 class="text-center">Choose an employee, start and end date to generate a report</h4>
    <p class="text-warning">${errorInvalidData}</p>
    <p class="text-warning">${errorNotSufficientData}</p>

    <form action="/reports/export-timesheets/process" method="post">
        <div class="form-group">
            <label for="employeeId">Select employee:</label>
            <select name="employeeId" class="form-control">
                <c:forEach items="${employees}" var="employee">
                    <option value="${employee.id}" id="employeeId">${employee.nameDisplay}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="startDateId">Select start date (must be Monday):</label>
            <input type="date" name="startDate" id="startDateId" class="form-control">
        </div>
        <div class="form-group">
            <label for="endDateId">Select end date (must be Sunday):</label>
            <input type="date" name="endDate" id="endDateId" class="form-control">
        </div>
        <input type="submit" value="Generate report" class="btn-success">
    </form>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
