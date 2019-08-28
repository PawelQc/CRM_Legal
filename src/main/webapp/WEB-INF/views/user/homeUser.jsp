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
<div class="home-right-div">
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

<div class="home-left-div">
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

<div class="home-bottom-div">
    <table class="table table-hover table-condensed">
        <tr>
            <th colspan="3">
                <h3 class="text-center">Recently added timesheet (project: ${recentTimesheet.project.signature})</h3>
            </th>
        </tr>
        <tr>
            <th class="date-row">Date</th>
            <th>Hours</th>
            <th>Work description</th>
        </tr>
        <tr>
            <td class="date-row">${recentTimesheet.workWeek.dateMonday}</td>
            <td>${recentTimesheet.workWeek.mondayHours}</td>
            <td>${recentTimesheet.workWeek.commentary.mondayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${recentTimesheet.workWeek.dateMonday.plusDays(1)}</td>
            <td>${recentTimesheet.workWeek.tuesdayHours}</td>
            <td>${recentTimesheet.workWeek.commentary.tuesdayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${recentTimesheet.workWeek.dateMonday.plusDays(2)}</td>
            <td>${recentTimesheet.workWeek.wednesdayHours}</td>
            <td>${recentTimesheet.workWeek.commentary.wednesdayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${recentTimesheet.workWeek.dateMonday.plusDays(3)}</td>
            <td>${recentTimesheet.workWeek.thursdayHours}</td>
            <td>${recentTimesheet.workWeek.commentary.thursdayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${recentTimesheet.workWeek.dateMonday.plusDays(4)}</td>
            <td>${recentTimesheet.workWeek.fridayHours}</td>
            <td>${recentTimesheet.workWeek.commentary.fridayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${recentTimesheet.workWeek.dateMonday.plusDays(5)}</td>
            <td>${recentTimesheet.workWeek.saturdayHours}</td>
            <td>${recentTimesheet.workWeek.commentary.saturdayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${recentTimesheet.workWeek.dateMonday.plusDays(6)}</td>
            <td>${recentTimesheet.workWeek.sundayHours}</td>
            <td>${recentTimesheet.workWeek.commentary.sundayCommentary}</td>
        </tr>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
