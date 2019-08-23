<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Timesheets employee-sorted</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Timesheets added by: ${timesheetsChosenUser.get(0).employee.nameDisplay}</h1>
    <a href="/timesheets/sort-by-employee?employeeId=${employeeId}&mode=prev&mondaySelect=${nextMonday}"
       class="btn btn-info rounded-0 text-light m-1" style="width: 90px">< Previous</a>
    <a href="/timesheets/sort-by-employee?employeeId=${employeeId}&mode=next&mondaySelect=${nextMonday}"
       class="btn btn-info rounded-0 text-light m-1" style="width: 90px; float: right">Next ></a>

    <table class="table table-condensed table-hover">
        <tr>
            <th colspan="10"><h4 class="text-center">Timesheet for: ${nextMonday.minusDays(21)}
                - ${nextMonday.minusDays(15)}</h4></th>
        </tr>
        <tr>
            <th>Project</th>
            <th>${nextMonday.minusDays(21)}</th>
            <th>${nextMonday.minusDays(20)}</th>
            <th>${nextMonday.minusDays(19)}</th>
            <th>${nextMonday.minusDays(18)}</th>
            <th>${nextMonday.minusDays(17)}</th>
            <th>${nextMonday.minusDays(16)}</th>
            <th>${nextMonday.minusDays(15)}</th>
            <th>Action</th>
        </tr>
        <c:forEach var="TSrefUnit" items="${timesheetsChosenUser}">
            <c:if test="${TSrefUnit.timesheetWeek.dateMonday.equals(nextMonday.minusDays(21))}">
                <tr>
                    <td>${TSrefUnit.project.signature}</td>
                    <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
                    <td>
                        <a href="/timesheets/details/${TSrefUnit.timesheetWeek.id}" target="_blank"
                           class="btn btn-info rounded-0 text-light m-1">Details</a>
                        <a href="/timesheets/update/${TSrefUnit.timesheetWeek.id}" target="_blank"
                           class="btn btn-warning rounded-0 text-light m-1">Update</a>
                        <a href="http://localhost:8080/timesheets/delete/${TSrefUnit.id}"
                           class="btn btn-danger rounded-0 text-light m-1"
                           onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>

    <table class="table table-hover table-condensed">
        <tr>
            <th colspan="10"><h4 class="text-center">Timesheet for: ${nextMonday.minusDays(14)}
                - ${nextMonday.minusDays(8)}</h4></th>
        </tr>
        <tr>
            <th>Project</th>
            <th>${nextMonday.minusDays(14)}</th>
            <th>${nextMonday.minusDays(13)}</th>
            <th>${nextMonday.minusDays(12)}</th>
            <th>${nextMonday.minusDays(11)}</th>
            <th>${nextMonday.minusDays(10)}</th>
            <th>${nextMonday.minusDays(9)}</th>
            <th>${nextMonday.minusDays(8)}</th>
            <th>Action</th>
        </tr>
        <c:forEach var="TSrefUnit" items="${timesheetsChosenUser}">
            <c:if test="${TSrefUnit.timesheetWeek.dateMonday.equals(nextMonday.minusDays(14))}">
                <tr>
                    <td>${TSrefUnit.project.signature}</td>
                    <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
                    <td>
                        <a href="/timesheets/details/${TSrefUnit.timesheetWeek.id}" target="_blank"
                           class="btn btn-info rounded-0 text-light m-1">Details</a>
                        <a href="/timesheets/update/${TSrefUnit.timesheetWeek.id}" target="_blank"
                           class="btn btn-warning rounded-0 text-light m-1">Update</a>
                        <a href="http://localhost:8080/timesheets/delete/${TSrefUnit.id}"
                           class="btn btn-danger rounded-0 text-light m-1"
                           onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>

    <table class="table table-hover table-condensed">
        <tr>
            <th colspan="10"><h4 class="text-center">Timesheet for: ${nextMonday.minusDays(7)}
                - ${nextMonday.minusDays(1)}</h4></th>
        </tr>
        <tr>
            <th>Project</th>
            <th>${nextMonday.minusDays(7)}</th>
            <th>${nextMonday.minusDays(6)}</th>
            <th>${nextMonday.minusDays(5)}</th>
            <th>${nextMonday.minusDays(4)}</th>
            <th>${nextMonday.minusDays(3)}</th>
            <th>${nextMonday.minusDays(2)}</th>
            <th>${nextMonday.minusDays(1)}</th>
            <th>Action</th>
        </tr>
        <c:forEach var="TSrefUnit" items="${timesheetsChosenUser}">
            <c:if test="${TSrefUnit.timesheetWeek.dateMonday.equals(nextMonday.minusDays(7))}">
                <tr>
                    <td>${TSrefUnit.project.signature}</td>
                    <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
                    <td>
                        <a href="/timesheets/details/${TSrefUnit.timesheetWeek.id}" target="_blank"
                           class="btn btn-info rounded-0 text-light m-1">Details</a>
                        <a href="/timesheets/update/${TSrefUnit.timesheetWeek.id}" target="_blank"
                           class="btn btn-warning rounded-0 text-light m-1">Update</a>
                        <a href="http://localhost:8080/timesheets/delete/${TSrefUnit.id}"
                           class="btn btn-danger rounded-0 text-light m-1"
                           onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>

    <table class="table table-hover table-condensed">
        <tr>
            <th colspan="10"><h4 class="text-center">Timesheet for: ${nextMonday} - ${nextMonday.plusDays(6)}</h4></th>
        </tr>
        <tr>
            <th>Project</th>
            <th>${nextMonday}</th>
            <th>${nextMonday.plusDays(1)}</th>
            <th>${nextMonday.plusDays(2)}</th>
            <th>${nextMonday.plusDays(3)}</th>
            <th>${nextMonday.plusDays(4)}</th>
            <th>${nextMonday.plusDays(5)}</th>
            <th>${nextMonday.plusDays(6)}</th>
            <th>Action</th>
        </tr>
        <c:forEach var="TSrefUnit" items="${timesheetsChosenUser}">
            <c:if test="${TSrefUnit.timesheetWeek.dateMonday.equals(nextMonday)}">
                <tr>
                    <td>${TSrefUnit.project.signature}</td>
                    <td>${TSrefUnit.timesheetWeek.mondayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.tuesdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.wednesdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.thursdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.fridayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.saturdayHours}</td>
                    <td>${TSrefUnit.timesheetWeek.sundayHours}</td>
                    <td>
                        <a href="/timesheets/details/${TSrefUnit.timesheetWeek.id}" target="_blank"
                           class="btn btn-info rounded-0 text-light m-1">Details</a>
                        <a href="/timesheets/update/${TSrefUnit.timesheetWeek.id}" target="_blank"
                           class="btn btn-warning rounded-0 text-light m-1">Update</a>
                        <a href="http://localhost:8080/timesheets/delete/${TSrefUnit.id}"
                           class="btn btn-danger rounded-0 text-light m-1"
                           onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
