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
            <h3 class="text-center">Previous month statistics (${uHomeParams.previousMonthFirstMonday}
                - ${uHomeParams.previousMonthFirstMonday.plusDays(27)})</h3>
        </th>
        <tr>
            <th>Billable hours</th>
            <td>${uHomeParams.amountOfBillableHours}</td>
        </tr>
        <tr>
            <th>Non billable hours</th>
            <td>${uHomeParams.amountOfNonBillableHours}</td>
        </tr>
        <tr>
            <th>Work time utilization level</th>
            <td>${uHomeParams.workTimeUtilizationLevel}%</td>
        </tr>
        <tr>
            <th>Was monthly target achieved? (${loggedInUser.additionalInfo.targetBudget} PLN)</th>
            <td>${uHomeParams.monthlyTargetAchieved}</td>
        </tr>
        <tr>
            <th>Value of rendered services</th>
            <td>${uHomeParams.valueOfRenderedServices} PLN</td>
        </tr>
        <tr>
            <th>Achieved bonus amount</th>
            <td>${uHomeParams.bonusAmount} PLN</td>
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
        <c:forEach var="project" items="${uHomeParams.projectsOfUser}">
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
                <h3 class="text-center">Recently added timesheet (project: ${uHomeParams.recentTimesheet.project.signature})</h3>
            </th>
        </tr>
        <tr>
            <th class="date-row">Date</th>
            <th>Hours</th>
            <th>Work description</th>
        </tr>
        <tr>
            <td class="date-row">${uHomeParams.recentTimesheet.workWeek.dateMonday}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.mondayHours}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.commentary.mondayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${uHomeParams.recentTimesheet.workWeek.dateMonday.plusDays(1)}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.tuesdayHours}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.commentary.tuesdayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${uHomeParams.recentTimesheet.workWeek.dateMonday.plusDays(2)}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.wednesdayHours}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.commentary.wednesdayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${uHomeParams.recentTimesheet.workWeek.dateMonday.plusDays(3)}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.thursdayHours}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.commentary.thursdayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${uHomeParams.recentTimesheet.workWeek.dateMonday.plusDays(4)}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.fridayHours}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.commentary.fridayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${uHomeParams.recentTimesheet.workWeek.dateMonday.plusDays(5)}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.saturdayHours}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.commentary.saturdayCommentary}</td>
        </tr>
        <tr>
            <td class="date-row">${uHomeParams.recentTimesheet.workWeek.dateMonday.plusDays(6)}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.sundayHours}</td>
            <td>${uHomeParams.recentTimesheet.workWeek.commentary.sundayCommentary}</td>
        </tr>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
