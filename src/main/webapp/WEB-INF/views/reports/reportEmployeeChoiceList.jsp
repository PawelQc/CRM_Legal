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
<h4>Choose an employee and month to generate a report: </h4>

<form action="/reports/monthly-employee-report/process" method="post">
    <label><h4>Select an employee</h4>
        <select name="employeeId">
            <c:forEach items="${employees}" var="employee">
                <option value="${employee.id}">${employee.nameDisplay}</option>
            </c:forEach>
        </select> <br><br>
    </label>

    <label><h4>Select report month</h4>
        <input type="month" name="month">
    </label>

    <input type="submit" value="Generate report">
</form>

</body>
</html>
