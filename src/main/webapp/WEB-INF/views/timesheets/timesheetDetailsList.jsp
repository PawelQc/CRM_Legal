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

<h3>Timesheet details</h3>
<h4>Project: ${timesheetDetails.project.signature}</h4>
<h4>Employee: ${timesheetDetails.employee.nameDisplay}</h4>
<h4>Week span: ${timesheetDetails.timesheetWeek.dateMonday}
    - ${timesheetDetails.timesheetWeek.dateMonday.plusDays(6)}</h4>

<table border="1">
    <tr>
        <th>Date</th>
        <th>Hours</th>
        <th>Work description</th>
    </tr>
    <tr>
        <td>${timesheetDetails.timesheetWeek.dateMonday}</td>
        <td>${timesheetDetails.timesheetWeek.mondayHours}</td>
        <td>${timesheetDetails.timesheetWeek.commentary.mondayCommentary}</td>
    </tr>
    <tr>
        <td>${timesheetDetails.timesheetWeek.dateMonday.plusDays(1)}</td>
        <td>${timesheetDetails.timesheetWeek.tuesdayHours}</td>
        <td>${timesheetDetails.timesheetWeek.commentary.tuesdayCommentary}</td>
    </tr>
    <tr>
        <td>${timesheetDetails.timesheetWeek.dateMonday.plusDays(2)}</td>
        <td>${timesheetDetails.timesheetWeek.wednesdayHours}</td>
        <td>${timesheetDetails.timesheetWeek.commentary.wednesdayCommentary}</td>
    </tr>
    <tr>
        <td>${timesheetDetails.timesheetWeek.dateMonday.plusDays(3)}</td>
        <td>${timesheetDetails.timesheetWeek.thursdayHours}</td>
        <td>${timesheetDetails.timesheetWeek.commentary.thursdayCommentary}</td>
    </tr>
    <tr>
        <td>${timesheetDetails.timesheetWeek.dateMonday.plusDays(4)}</td>
        <td>${timesheetDetails.timesheetWeek.fridayHours}</td>
        <td>${timesheetDetails.timesheetWeek.commentary.fridayCommentary}</td>
    </tr>
    <tr>
        <td>${timesheetDetails.timesheetWeek.dateMonday.plusDays(5)}</td>
        <td>${timesheetDetails.timesheetWeek.saturdayHours}</td>
        <td>${timesheetDetails.timesheetWeek.commentary.saturdayCommentary}</td>
    </tr>
    <tr>
        <td>${timesheetDetails.timesheetWeek.dateMonday.plusDays(6)}</td>
        <td>${timesheetDetails.timesheetWeek.sundayHours}</td>
        <td>${timesheetDetails.timesheetWeek.commentary.sundayCommentary}</td>
    </tr>
</table>
<br>
<a href="/timesheets/update/${timesheetDetails.timesheetWeek.id}">Update</a>
<a href="http://localhost:8080/timesheets/delete/${timesheetDetails.id}/${timesheetDetails.timesheetWeek.id}"
   onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
