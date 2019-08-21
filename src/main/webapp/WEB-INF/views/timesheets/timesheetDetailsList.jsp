<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Timesheet details</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Timesheet details</h1>
    <h4 class="text-center">Project: ${timesheetDetails.project.signature}</h4>
    <h4 class="text-center">Employee: ${timesheetDetails.employee.nameDisplay}</h4>
    <h4 class="text-center">Time span: ${timesheetDetails.timesheetWeek.dateMonday}
        - ${timesheetDetails.timesheetWeek.dateMonday.plusDays(6)}</h4>

    <table class="table table-hover">
        <tr>
            <th style="width: 130px">Date</th>
            <th>Hours</th>
            <th>Work description</th>
        </tr>
        <tr>
            <td style="width: 130px">${timesheetDetails.timesheetWeek.dateMonday}</td>
            <td>${timesheetDetails.timesheetWeek.mondayHours}</td>
            <td>${timesheetDetails.timesheetWeek.commentary.mondayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${timesheetDetails.timesheetWeek.dateMonday.plusDays(1)}</td>
            <td>${timesheetDetails.timesheetWeek.tuesdayHours}</td>
            <td>${timesheetDetails.timesheetWeek.commentary.tuesdayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${timesheetDetails.timesheetWeek.dateMonday.plusDays(2)}</td>
            <td>${timesheetDetails.timesheetWeek.wednesdayHours}</td>
            <td>${timesheetDetails.timesheetWeek.commentary.wednesdayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${timesheetDetails.timesheetWeek.dateMonday.plusDays(3)}</td>
            <td>${timesheetDetails.timesheetWeek.thursdayHours}</td>
            <td>${timesheetDetails.timesheetWeek.commentary.thursdayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${timesheetDetails.timesheetWeek.dateMonday.plusDays(4)}</td>
            <td>${timesheetDetails.timesheetWeek.fridayHours}</td>
            <td>${timesheetDetails.timesheetWeek.commentary.fridayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${timesheetDetails.timesheetWeek.dateMonday.plusDays(5)}</td>
            <td>${timesheetDetails.timesheetWeek.saturdayHours}</td>
            <td>${timesheetDetails.timesheetWeek.commentary.saturdayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${timesheetDetails.timesheetWeek.dateMonday.plusDays(6)}</td>
            <td>${timesheetDetails.timesheetWeek.sundayHours}</td>
            <td>${timesheetDetails.timesheetWeek.commentary.sundayCommentary}</td>
        </tr>
    </table>
    <div style="text-align: center">
        <a href="/timesheets/update/${timesheetDetails.timesheetWeek.id}"
           class="btn btn-warning rounded-0 text-light m-1" style="margin-right: 10px">Update</a>
        <a href="http://localhost:8080/timesheets/delete/${timesheetDetails.id}"
           class="btn btn-danger rounded-0 text-light m-1"
           onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
    </div>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
