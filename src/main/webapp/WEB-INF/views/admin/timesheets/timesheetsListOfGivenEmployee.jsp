<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Timesheets - sorted by employee</h3>


<table border="1">
    <tr>
        <th>No</th>
        <th>Monday</th>
        <th>MonHours</th>
        <th>TueHours</th>
        <th>WedHours</th>
        <th>ThHours</th>
        <th>FriHours</th>
        <th>SatHours</th>
        <th>SunHours</th>
        <th>Project</th>
        <th>Employee</th>
    </tr>
    <c:forEach var="TSrefUnit" items="${timesheets}" varStatus="count">
        <tr>
            <td>${count.count}</td>
            <td>${TSrefUnit.timesheetWeek.dateMonday}</td>
            <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
            <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
            <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
            <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
            <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
            <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
            <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
            <td>${TSrefUnit.project.signature}</td>
            <td>${TSrefUnit.employee.lastName}</td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
