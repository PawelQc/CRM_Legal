<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Invoice preview</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Invoice preview for client: ${client.nameDisplay}</h1>
    <h3 class="text-center">Time interval: ${selectedMonday} - ${selectedMonday.plusDays(27)}</h3>

    <c:forEach var="project" items="${projectsOfClient}">
        <table class="table table-hover table-condensed">
            <tr>
                <th class="text-center" colspan="4">Project: ${project.signature}</th>
            </tr>
            <tr>
                <th>Lawyer</th>
                <th>Week</th>
                <th>Hours</th>
                <th>Description</th>
            </tr>
            <c:forEach items="${timesheets}" var="timesheet">
                <c:if test="${timesheet.project.id eq project.id}">
                    <tr>
                        <td>${timesheet.employee.nameDisplay}</td>
                        <td>${timesheet.workWeek.dateMonday}
                            - ${timesheet.workWeek.dateMonday.plusDays(6)}</td>
                        <td>${timesheet.countWeekHours()}</td>
                        <td><a href="/timesheets/details/${timesheet.workWeek.id}" target="_blank"
                               class="btn btn-info rounded-0 text-light m-1">Details</a></td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </c:forEach>
    <table class="table table-condensed table-hover">
        <tr>
            <th style="width: 200px">Total hours</th>
            <td>${amountOfHours}</td>
        </tr>
        <tr>
            <th style="width: 200px">Hourly rate</th>
            <td>${client.additionalInfo.hourlyRateIsCharged} PLN</td>
        </tr>
        <tr>
            <th style="width: 200px">Net invoice amount</th>
            <td>${amountOfHours * client.additionalInfo.hourlyRateIsCharged} PLN</td>
        </tr>
        <tr>
            <th style="width: 200px">Output VAT</th>
            <td>${amountOfHours * client.additionalInfo.hourlyRateIsCharged * 0.23} PLN</td>
        </tr>
        <tr>
            <th style="width: 200px">Due amount</th>
            <td>${amountOfHours * client.additionalInfo.hourlyRateIsCharged * 1.23} PLN</td>
        </tr>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
