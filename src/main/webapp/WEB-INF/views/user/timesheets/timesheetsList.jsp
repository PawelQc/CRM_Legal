<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Timesheets</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>
<div class="container">
    <h1 class="text-center">Timesheets added between ${nextMonday.minusDays(21)} - ${nextMonday.plusDays(6)}</h1>
    <h4 class="text-warning">${errorNoTimesheets}</h4>

    <div class="text-center">
        <div class="ts-form-sort">
            <form action="<c:url value="/timesheets/sort-by-project"/>" method="post">
                <select name="projectId" id="projectId" class="form-control">
                    <c:forEach items="${projectsUser}" var="project">
                        <option value="${project.id}">${project.signature}</option>
                    </c:forEach>
                </select> <br>
                <input type="submit" value="Sort" class="btn-success">
            </form>
        </div>
    </div>
    <a href="<c:url value="/timesheets/choose-project"/>" class="btn btn-success rounded-0 text-light m-1 btn-ts-add">Add</a>
    <br> <br>

    <a href="<c:url value="/timesheets/list?mode=prev&mondaySelect=${nextMonday}"/>"
       class="btn btn-info rounded-0 text-light m-1 btn-ts-prev">< Previous</a>
    <a href="<c:url value="/timesheets/list?mode=next&mondaySelect=${nextMonday}"/>"
       class="btn btn-info rounded-0 text-light m-1 btn-ts-next">Next ></a>

    <table class="table table-hover table-condensed">
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
        <c:forEach var="timesheet" items="${timesheetsUser}">
            <c:if test="${timesheet.workWeek.dateMonday.equals(nextMonday.minusDays(21))}">
                <tr>
                    <td>${timesheet.project.signature}</td>
                    <td>${timesheet.workWeek.mondayHours}</td>
                    <td>${timesheet.workWeek.tuesdayHours}</td>
                    <td>${timesheet.workWeek.wednesdayHours}</td>
                    <td>${timesheet.workWeek.thursdayHours}</td>
                    <td>${timesheet.workWeek.fridayHours}</td>
                    <td>${timesheet.workWeek.saturdayHours}</td>
                    <td>${timesheet.workWeek.sundayHours}</td>
                    <td>
                        <a href="/timesheets/details/${timesheet.workWeek.id}"
                           class="btn btn-info rounded-0 text-light m-1">Details</a>
                        <a href="/timesheets/update/${timesheet.workWeek.id}"
                           class="btn btn-warning rounded-0 text-light m-1">Update</a>
                        <a href="/timesheets/delete/${timesheet.id}"
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
        <c:forEach var="timesheet" items="${timesheetsUser}">
            <c:if test="${timesheet.workWeek.dateMonday.equals(nextMonday.minusDays(14))}">
                <tr>
                    <td>${timesheet.project.signature}</td>
                    <td>${timesheet.workWeek.mondayHours}</td>
                    <td>${timesheet.workWeek.tuesdayHours}</td>
                    <td>${timesheet.workWeek.wednesdayHours}</td>
                    <td>${timesheet.workWeek.thursdayHours}</td>
                    <td>${timesheet.workWeek.fridayHours}</td>
                    <td>${timesheet.workWeek.saturdayHours}</td>
                    <td>${timesheet.workWeek.sundayHours}</td>
                    <td>
                        <a href="/timesheets/details/${timesheet.workWeek.id}"
                           class="btn btn-info rounded-0 text-light m-1">Details</a>
                        <a href="/timesheets/update/${timesheet.workWeek.id}"
                           class="btn btn-warning rounded-0 text-light m-1">Update</a>
                        <a href="/timesheets/delete/${timesheet.id}"
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
        <c:forEach var="timesheet" items="${timesheetsUser}">
            <c:if test="${timesheet.workWeek.dateMonday.equals(nextMonday.minusDays(7))}">
                <tr>
                    <td>${timesheet.project.signature}</td>
                    <td>${timesheet.workWeek.mondayHours}</td>
                    <td>${timesheet.workWeek.tuesdayHours}</td>
                    <td>${timesheet.workWeek.wednesdayHours}</td>
                    <td>${timesheet.workWeek.thursdayHours}</td>
                    <td>${timesheet.workWeek.fridayHours}</td>
                    <td>${timesheet.workWeek.saturdayHours}</td>
                    <td>${timesheet.workWeek.sundayHours}</td>
                    <td>
                        <a href="/timesheets/details/${timesheet.workWeek.id}"
                           class="btn btn-info rounded-0 text-light m-1">Details</a>
                        <a href="/timesheets/update/${timesheet.workWeek.id}"
                           class="btn btn-warning rounded-0 text-light m-1">Update</a>
                        <a href="/timesheets/delete/${timesheet.id}"
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
        <c:forEach var="timesheet" items="${timesheetsUser}">
            <c:if test="${timesheet.workWeek.dateMonday.equals(nextMonday)}">
                <tr>
                    <td>${timesheet.project.signature}</td>
                    <td>${timesheet.workWeek.mondayHours}</td>
                    <td>${timesheet.workWeek.tuesdayHours}</td>
                    <td>${timesheet.workWeek.wednesdayHours}</td>
                    <td>${timesheet.workWeek.thursdayHours}</td>
                    <td>${timesheet.workWeek.fridayHours}</td>
                    <td>${timesheet.workWeek.saturdayHours}</td>
                    <td>${timesheet.workWeek.sundayHours}</td>
                    <td>
                        <a href="/timesheets/details/${timesheet.workWeek.id}"
                           class="btn btn-info rounded-0 text-light m-1">Details</a>
                        <a href="/timesheets/update/${timesheet.workWeek.id}"
                           class="btn btn-warning rounded-0 text-light m-1">Update</a>
                        <a href="/timesheets/delete/${timesheet.id}"
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
