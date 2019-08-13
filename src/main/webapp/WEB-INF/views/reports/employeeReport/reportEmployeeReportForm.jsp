<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Monthly employee report</h3>
<h4>Choose an employee and start date to generate a report (4-week interval)</h4>

<h4>${errorInvalidData}</h4> <h4>${errorNotSufficientData}</h4>

<form action="/reports/monthly-employee-report/process" method="post">
    <label><h4>Select employee</h4>
        <select name="employeeId">
            <c:forEach items="${employees}" var="employee">
                <option value="${employee.id}">${employee.nameDisplay}</option>
            </c:forEach>
        </select> <br><br>
    </label>

    <label><h4>Select start date (must be first Monday of a month)</h4>
        <input type="date" name="startDate">
    </label><br><br>

    <input type="submit" value="Generate report">
</form>

</body>
</html>
