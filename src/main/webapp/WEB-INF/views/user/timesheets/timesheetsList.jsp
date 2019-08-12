<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Timesheets added within last 4 weeks</h3>

<a href="/timesheets/choose-project">Add</a>

<form action="/timesheets/sort-by-project" method="post">
    <label><h4>Sort by project</h4>
        <select name="projectId">
            <c:forEach items="${projectsWhereEmployeeParticipates}" var="project">
                <option value="${project.id}">${project.signature}</option>
            </c:forEach>
        </select> <br><br>
        <input type="submit" value="Sort">
    </label>
</form>

<%--todo jak zrobić żeby jak tabela jest pusta wyświetlić tylko 1 raz że nie ma rekordów??--%>

<a href="/timesheets/list?mode=prev&mondaySelect=${nextMonday}">Previous 4 weeks</a>
<a href="/timesheets/list?mode=next&mondaySelect=${nextMonday}">Next 4 weeks</a>
<table border="1">
    <tr>
        <th colspan="10">Timesheet for: ${nextMonday.minusDays(21)} - ${nextMonday.minusDays(15)}</th>
    </tr>
    <tr>
        <th>Project</th>
        <th>Employee</th>
        <th>${nextMonday.minusDays(21)}</th>
        <th>${nextMonday.minusDays(20)}</th>
        <th>${nextMonday.minusDays(19)}</th>
        <th>${nextMonday.minusDays(18)}</th>
        <th>${nextMonday.minusDays(17)}</th>
        <th>${nextMonday.minusDays(16)}</th>
        <th>${nextMonday.minusDays(15)}</th>
        <th>Action</th>
    </tr>
    <c:forEach var="TSrefUnit" items="${timesheetReferenceUnitsUser}">
        <c:if test="${TSrefUnit.timesheetWeek.dateMonday.equals(nextMonday.minusDays(21))}">
            <tr>
                <td>${TSrefUnit.project.signature}</td>
                <td>${TSrefUnit.employee.lastName}</td>
                <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
                <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
                <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
                <td>
                    <a href="/timesheets/update/${TSrefUnit.timesheetWeek.id}">Update</a>
                    <a href="http://localhost:8080/timesheets/delete/${TSrefUnit.id}/${TSrefUnit.timesheetWeek.id}"
                       onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>
<br>
<table border="1">
    <tr>
        <th colspan="10">Timesheet for: ${nextMonday.minusDays(14)} - ${nextMonday.minusDays(8)}</th>
    </tr>
    <tr>
        <th>Project</th>
        <th>Employee</th>
        <th>${nextMonday.minusDays(14)}</th>
        <th>${nextMonday.minusDays(13)}</th>
        <th>${nextMonday.minusDays(12)}</th>
        <th>${nextMonday.minusDays(11)}</th>
        <th>${nextMonday.minusDays(10)}</th>
        <th>${nextMonday.minusDays(9)}</th>
        <th>${nextMonday.minusDays(8)}</th>
        <th>Action</th>
    </tr>
    <c:forEach var="TSrefUnit" items="${timesheetReferenceUnitsUser}">
        <c:if test="${TSrefUnit.timesheetWeek.dateMonday.equals(nextMonday.minusDays(14))}">
            <tr>
                <td>${TSrefUnit.project.signature}</td>
                <td>${TSrefUnit.employee.lastName}</td>
                <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
                <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
                <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
                <td>
                    <a href="/timesheets/update/${TSrefUnit.timesheetWeek.id}">Update</a>
                    <a href="http://localhost:8080/timesheets/delete/${TSrefUnit.id}/${TSrefUnit.timesheetWeek.id}"
                       onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>
<br>

<table border="1">
    <tr>
        <th colspan="10">Timesheet for: ${nextMonday.minusDays(7)} - ${nextMonday.minusDays(1)}</th>
    </tr>
    <tr>
        <th>Project</th>
        <th>Employee</th>
        <th>${nextMonday.minusDays(7)}</th>
        <th>${nextMonday.minusDays(6)}</th>
        <th>${nextMonday.minusDays(5)}</th>
        <th>${nextMonday.minusDays(4)}</th>
        <th>${nextMonday.minusDays(3)}</th>
        <th>${nextMonday.minusDays(2)}</th>
        <th>${nextMonday.minusDays(1)}</th>
        <th>Action</th>
    </tr>
    <c:forEach var="TSrefUnit" items="${timesheetReferenceUnitsUser}">
        <c:if test="${TSrefUnit.timesheetWeek.dateMonday.equals(nextMonday.minusDays(7))}">
            <tr>
                <td>${TSrefUnit.project.signature}</td>
                <td>${TSrefUnit.employee.lastName}</td>
                <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
                <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
                <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
                <td>
                    <a href="/timesheets/update/${TSrefUnit.timesheetWeek.id}">Update</a>
                    <a href="http://localhost:8080/timesheets/delete/${TSrefUnit.id}/${TSrefUnit.timesheetWeek.id}"
                       onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>
<br>
<table border="1">
    <tr>
        <th colspan="10">Timesheet for: ${nextMonday} - ${nextMonday.plusDays(6)}</th>
    </tr>
    <tr>
        <th>Project</th>
        <th>Employee</th>
        <th>${nextMonday}</th>
        <th>${nextMonday.plusDays(1)}</th>
        <th>${nextMonday.plusDays(2)}</th>
        <th>${nextMonday.plusDays(3)}</th>
        <th>${nextMonday.plusDays(4)}</th>
        <th>${nextMonday.plusDays(5)}</th>
        <th>${nextMonday.plusDays(6)}</th>
        <th>Action</th>
    </tr>
    <c:forEach var="TSrefUnit" items="${timesheetReferenceUnitsUser}">
        <c:if test="${TSrefUnit.timesheetWeek.dateMonday.equals(nextMonday)}">
            <tr>
                <td>${TSrefUnit.project.signature}</td>
                <td>${TSrefUnit.employee.lastName}</td>
                <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
                <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
                <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
                <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
                <td>
                    <a href="/timesheets/update/${TSrefUnit.timesheetWeek.id}">Update</a>
                    <a href="http://localhost:8080/timesheets/delete/${TSrefUnit.id}/${TSrefUnit.timesheetWeek.id}"
                       onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>
<br>

</body>
</html>
