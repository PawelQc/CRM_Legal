<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Timesheets - sorted by project</h3>
<h4>Project signature: ${timesheets.get(0).project.signature}</h4>

<c:forEach var="TSrefUnit" items="${timesheets}" >
    <table border="1">
        <tr>
            <th>${TSrefUnit.timesheetWeek.dateMonday}</th>
            <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(1)}</th>
            <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(2)}</th>
            <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(3)}</th>
            <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(4)}</th>
            <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(5)}</th>
            <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(6)}</th>
        </tr>
        <tr>
            <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
            <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
            <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
            <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
            <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
            <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
            <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
        </tr>
    </table>
    <br>
</c:forEach>

</body>
</html>
