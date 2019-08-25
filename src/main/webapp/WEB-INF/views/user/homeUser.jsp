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

<h1 class="text-center">Your recent activity</h1> <br>
<div style="width: 600px; float: right; display: inline-block; margin-right: 100px">
    <table class="table table-hover table-condensed">
        <th colspan="2">
            <h3 class="text-center">Previous month statistics (${previousMonthFirstMonday}
                - ${previousMonthFirstMonday.plusDays(27)})</h3>
        </th>
        <tr>
            <th>Billable hours</th>
            <td>${amountOfBillableHours}</td>
        </tr>
        <tr>
            <th>Non billable hours</th>
            <td>${amountOfNonBillableHours}</td>
        </tr>
        <tr>
            <th>Work time utilization level</th>
            <td>${workTimeUtilizationLevel}%</td>
        </tr>
        <tr>
            <th>Was monthly target achieved? (${loggedInUser.additionalInfo.targetBudget} PLN)</th>
            <td>${isMonthlyTargetAchieved}</td>
        </tr>
        <tr>
            <th>Value of rendered services</th>
            <td>${valueOfRenderedServices} PLN</td>
        </tr>
        <tr>
            <th>Achieved bonus amount</th>
            <td>${bonusAmount} PLN</td>
        </tr>
    </table>
</div>
<div></div>

<div style="width: 600px; float: left; display: inline-block; margin-left: 100px; margin-bottom: 80px">
    <table class="table table-hover table-condensed">
        <tr>
            <th colspan="2">
                <h3 class="text-center">Your projects</h3>
            </th>
        </tr>
        <tr>
            <th class="text-center">Client</th>
            <th class="text-center">Signature</th>
        </tr>
        <c:forEach var="project" items="${projectsOfUser}">
            <tr>
                <td class="text-center">${project.client.nameDisplay}</td>
                <td class="text-center">${project.signature}</td>
            </tr>

        </c:forEach>
    </table>
</div>

<div style="width: 1000px; margin-left: auto; margin-right: auto; margin-top: auto">
    <table class="table table-hover table-condensed">
        <tr>
            <th colspan="3">
                <h3 class="text-center">Recently added timesheet (project: ${recentTimesheet.project.signature})</h3>
            </th>
        </tr>
        <tr>
            <th style="width: 130px">Date</th>
            <th>Hours</th>
            <th>Work description</th>
        </tr>
        <tr>
            <td style="width: 130px">${recentTimesheet.timesheetWeek.dateMonday}</td>
            <td>${recentTimesheet.timesheetWeek.mondayHours}</td>
            <td>${recentTimesheet.timesheetWeek.commentary.mondayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${recentTimesheet.timesheetWeek.dateMonday.plusDays(1)}</td>
            <td>${recentTimesheet.timesheetWeek.tuesdayHours}</td>
            <td>${recentTimesheet.timesheetWeek.commentary.tuesdayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${recentTimesheet.timesheetWeek.dateMonday.plusDays(2)}</td>
            <td>${recentTimesheet.timesheetWeek.wednesdayHours}</td>
            <td>${recentTimesheet.timesheetWeek.commentary.wednesdayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${recentTimesheet.timesheetWeek.dateMonday.plusDays(3)}</td>
            <td>${recentTimesheet.timesheetWeek.thursdayHours}</td>
            <td>${recentTimesheet.timesheetWeek.commentary.thursdayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${recentTimesheet.timesheetWeek.dateMonday.plusDays(4)}</td>
            <td>${recentTimesheet.timesheetWeek.fridayHours}</td>
            <td>${recentTimesheet.timesheetWeek.commentary.fridayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${recentTimesheet.timesheetWeek.dateMonday.plusDays(5)}</td>
            <td>${recentTimesheet.timesheetWeek.saturdayHours}</td>
            <td>${recentTimesheet.timesheetWeek.commentary.saturdayCommentary}</td>
        </tr>
        <tr>
            <td style="width: 130px">${recentTimesheet.timesheetWeek.dateMonday.plusDays(6)}</td>
            <td>${recentTimesheet.timesheetWeek.sundayHours}</td>
            <td>${recentTimesheet.timesheetWeek.commentary.sundayCommentary}</td>
        </tr>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
