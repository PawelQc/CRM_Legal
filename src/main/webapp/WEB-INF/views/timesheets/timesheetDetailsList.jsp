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
    <h4 class="text-center">Time span: ${timesheetDetails.workWeek.dateMonday}
        - ${timesheetDetails.workWeek.dateMonday.plusDays(6)}</h4>

    <div class="container">
        <table class="table table-hover" style="width: 1200px; table-layout: auto">
            <tr>
                <th style="width: 130px">Date</th>
                <th>Hours</th>
                <th>Work description</th>
            </tr>
            <tr>
                <td style="width: 130px">${timesheetDetails.workWeek.dateMonday}</td>
                <td>${timesheetDetails.workWeek.mondayHours}</td>
                <td>${timesheetDetails.workWeek.commentary.mondayCommentary}</td>
            </tr>
            <tr>
                <td style="width: 130px">${timesheetDetails.workWeek.dateMonday.plusDays(1)}</td>
                <td>${timesheetDetails.workWeek.tuesdayHours}</td>
                <td>${timesheetDetails.workWeek.commentary.tuesdayCommentary}</td>
            </tr>
            <tr>
                <td style="width: 130px">${timesheetDetails.workWeek.dateMonday.plusDays(2)}</td>
                <td>${timesheetDetails.workWeek.wednesdayHours}</td>
                <td>${timesheetDetails.workWeek.commentary.wednesdayCommentary}</td>
            </tr>
            <tr>
                <td style="width: 130px">${timesheetDetails.workWeek.dateMonday.plusDays(3)}</td>
                <td>${timesheetDetails.workWeek.thursdayHours}</td>
                <td>${timesheetDetails.workWeek.commentary.thursdayCommentary}</td>
            </tr>
            <tr>
                <td style="width: 130px">${timesheetDetails.workWeek.dateMonday.plusDays(4)}</td>
                <td>${timesheetDetails.workWeek.fridayHours}</td>
                <td>${timesheetDetails.workWeek.commentary.fridayCommentary}</td>
            </tr>
            <tr>
                <td style="width: 130px">${timesheetDetails.workWeek.dateMonday.plusDays(5)}</td>
                <td>${timesheetDetails.workWeek.saturdayHours}</td>
                <td>${timesheetDetails.workWeek.commentary.saturdayCommentary}</td>
            </tr>
            <tr>
                <td style="width: 130px">${timesheetDetails.workWeek.dateMonday.plusDays(6)}</td>
                <td>${timesheetDetails.workWeek.sundayHours}</td>
                <td>${timesheetDetails.workWeek.commentary.sundayCommentary}</td>
            </tr>
        </table>
    </div>
    <div style="text-align: center">
        <a href="/timesheets/update/${timesheetDetails.workWeek.id}"
           class="btn btn-warning rounded-0 text-light m-1" style="margin-right: 10px">Update</a>
        <a href="http://localhost:8080/timesheets/delete/${timesheetDetails.id}"
           class="btn btn-danger rounded-0 text-light m-1"
           onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
    </div>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
