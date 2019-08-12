<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Timesheets - recently added by all employees</h3>

<a href="/timesheets/choose-project">Add</a>

<form action="/timesheets/sort-by-project" method="post">
    <label><h4>Sort by project</h4>
        <select name="projectId">
            <c:forEach items="${projects}" var="project">
                <option value="${project.id}">${project.signature}</option>
            </c:forEach>
        </select> <br><br>
        <input type="submit" value="Sort">
    </label>
</form>

<form action="/timesheets/sort-by-employee" method="post">
    <label><h4>Sort by employee</h4>
        <select name="employeeId">
            <c:forEach items="${employees}" var="employee">
                <option value="${employee.id}">${employee.firstName} ${employee.lastName}</option>
            </c:forEach>
        </select> <br><br>
        <input type="submit" value="Sort">
    </label>
</form>

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
        <th>Action</th>
    </tr>
    <c:forEach var="TSrefUnit" items="${timesheetReferenceUnitsAll}" varStatus="count">
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
            <td>
                <a href="/timesheets/update/${TSrefUnit.timesheetWeek.id}">Update</a>
                <a href="http://localhost:8080/timesheets/delete/${TSrefUnit.id}/${TSrefUnit.timesheetWeek.id}"
                   onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
