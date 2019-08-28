<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Home</title>
    <%@ include file="/fragments/head.jspf" %>

</head>
<body>
<%@ include file="/fragments/header.jspf" %>


<h1 class="text-center">Recent activity of all employees</h1> <br>
<div class="home-right-div">
    <table class="table table-hover table-condensed">
        <th colspan="2">
            <h3 class="text-center"> Work time utilisation for: ${thisMonthFirstMonday}
                - ${thisMonthFirstMonday.plusDays(27)}</h3>
        </th>
        <tr>
            <th>Employee</th>
            <th>Utilisation level</th>
        </tr>
        <c:forEach items="${employeesAndUtilisation}" var="entry">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.value}%</td>
            </tr>
        </c:forEach>
    </table>
    <%--    todo wykresy?--%>
    <%--    <a href="/home/chart-employees-hours" style="margin-right: 10px"--%>
    <%--       class="btn btn-warning rounded-0 text-light m-1">Billable vs non-billable - chart</a>--%>
    <%--    <a href="/home/chart-employees-utilisation"--%>
    <%--       class="btn btn-success rounded-0 text-light m-1">Utilisation comparision - chart</a>--%>
</div>
<div></div>

<div class="home-left-div">
    <table class="table table-hover table-condensed">
        <tr>
            <th colspan="2">
                <h3 class="text-center">Projects and work hours for: ${thisMonthFirstMonday}
                    - ${thisMonthFirstMonday.plusDays(27)}</h3>
            </th>
        </tr>
        <tr>
            <th class="text-center">Project</th>
            <th class="text-center">Hours</th>
        </tr>
        <c:forEach var="entry" items="${projectsAndHours}">
            <tr>
                <td class="text-center">${entry.key}</td>
                <td class="text-center">${entry.value}</td>
            </tr>
        </c:forEach>
    </table>
    <%--    todo wykresy?--%>
    <%--    <a href="/home/chart-project-hours"--%>
    <%--       class="btn btn-warning rounded-0 text-light m-1">Projects and hours - chart</a>--%>
    <%--    <a href="/home/chart-total-hours"--%>
    <%--       class="btn btn-success rounded-0 text-light m-1">Total hours vs project - chart</a>--%>
</div>

<div class="container">
    <table class="table table-condensed table-hover">
        <tr>
            <th colspan="10"><h3 class="text-center">Recent timesheets: ${previousMonday}
                - ${previousMonday.plusDays(6)}</h3></th>
        </tr>
        <tr>
            <th>Employee</th>
            <th>Project</th>
            <th>${previousMonday}</th>
            <th>${previousMonday.plusDays(1)}</th>
            <th>${previousMonday.plusDays(2)}</th>
            <th>${previousMonday.plusDays(3)}</th>
            <th>${previousMonday.plusDays(4)}</th>
            <th>${previousMonday.plusDays(5)}</th>
            <th>${previousMonday.plusDays(6)}</th>
            <th>Action</th>
        </tr>
        <c:forEach var="TSrefUnit" items="${timesheets}">
            <tr>
                <td>${TSrefUnit.employee.nameDisplay}</td>
                <td>${TSrefUnit.project.signature}</td>
                <td>${TSrefUnit.workWeek.mondayHours}</td>
                <td>${TSrefUnit.workWeek.tuesdayHours}</td>
                <td>${TSrefUnit.workWeek.wednesdayHours}</td>
                <td>${TSrefUnit.workWeek.thursdayHours}</td>
                <td>${TSrefUnit.workWeek.fridayHours}</td>
                <td>${TSrefUnit.workWeek.saturdayHours}</td>
                <td>${TSrefUnit.workWeek.sundayHours}</td>
                <td>
                    <a href="/timesheets/details/${TSrefUnit.workWeek.id}" target="_blank"
                       class="btn btn-info rounded-0 text-light m-1">Details</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
