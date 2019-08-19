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

    <c:forEach var="TSrefUnit" items="${timesheets}">
        <table class="table table-hover table">
            <tr>
                <th>${TSrefUnit.timesheetWeek.dateMonday}</th>
                <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(1)}</th>
                <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(2)}</th>
                <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(3)}</th>
                <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(4)}</th>
                <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(5)}</th>
                <th>${TSrefUnit.timesheetWeek.dateMonday.plusDays(6)}</th>
                <th>Action</th>
            </tr>
            <tr>
                <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
                <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
                <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
                <td>
                    <a href="/timesheets/details/${TSrefUnit.timesheetWeek.id}"
                       class="btn btn-info rounded-0 text-light m-1">Details</a>
                    <a href="/timesheets/update/${TSrefUnit.timesheetWeek.id}"
                       class="btn btn-warning rounded-0 text-light m-1">Update</a>
                    <a href="http://localhost:8080/timesheets/delete/${TSrefUnit.id}/${TSrefUnit.timesheetWeek.id}"
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
