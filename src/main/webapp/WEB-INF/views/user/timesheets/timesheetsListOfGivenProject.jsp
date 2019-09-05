<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Timesheets project-sorted</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Timesheets of project: ${timesheets.get(0).project.signature}</h1>

    <c:forEach var="timesheet" items="${timesheets}">
        <table class="table table-hover table-condensed">
            <tr>
                <th>${timesheet.workWeek.dateMonday}</th>
                <th>${timesheet.workWeek.dateMonday.plusDays(1)}</th>
                <th>${timesheet.workWeek.dateMonday.plusDays(2)}</th>
                <th>${timesheet.workWeek.dateMonday.plusDays(3)}</th>
                <th>${timesheet.workWeek.dateMonday.plusDays(4)}</th>
                <th>${timesheet.workWeek.dateMonday.plusDays(5)}</th>
                <th>${timesheet.workWeek.dateMonday.plusDays(6)}</th>
                <th>Action</th>
            </tr>
            <tr>
                <td>${timesheet.workWeek.mondayHours}</td>
                <td>${timesheet.workWeek.tuesdayHours}</td>
                <td>${timesheet.workWeek.wednesdayHours}</td>
                <td>${timesheet.workWeek.thursdayHours}</td>
                <td>${timesheet.workWeek.fridayHours}</td>
                <td>${timesheet.workWeek.saturdayHours}</td>
                <td>${timesheet.workWeek.sundayHours}</td>
                <td>
                    <a href="/timesheets/details/${timesheet.workWeek.id}" target="_blank"
                       class="btn btn-info rounded-0 text-light m-1">Details</a>
                    <a href="/timesheets/update/${timesheet.workWeek.id}"
                       class="btn btn-warning rounded-0 text-light m-1">Update</a>
                    <a href="/timesheets/delete/${timesheet.id}"
                       class="btn btn-danger rounded-0 text-light m-1"
                       onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
                </td>
            </tr>
        </table>
        <br>
    </c:forEach>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
