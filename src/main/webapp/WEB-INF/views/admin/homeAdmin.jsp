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
<div style="width: 600px; float: right; display: inline-block; margin-right: 100px">
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
</div>
<div></div>

<div style="width: 600px; float: left; display: inline-block; margin-left: 100px; margin-bottom: 80px">
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
</div>

<div class="container">
    <table class="table table-condensed table-hover">
        <tr>
            <th colspan="10"><h4 class="text-center">Recent timesheets: ${previousMonday}
                - ${previousMonday.plusDays(6)}</h4></th>
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
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
