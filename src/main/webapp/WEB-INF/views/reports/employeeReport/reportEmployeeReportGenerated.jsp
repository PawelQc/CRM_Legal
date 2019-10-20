<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Employee report</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Monthly employee report for: ${employeeReport.reportedEmployee.nameDisplay}</h1>
    <h3 class="text-center">Time interval: ${employeeReport.selectedMonday}
        - ${employeeReport.selectedMonday.plusDays(27)}</h3>
    <table class="table table-hover">
        <tr>
            <th>Billable hours</th>
            <td>${employeeReport.amountOfBillableHours}</td>
        </tr>
        <tr>
            <th>Non billable hours</th>
            <td>${employeeReport.amountOfNonBillableHours}</td>
        </tr>
        <tr>
            <th>Work time utilization level</th>
            <td>${employeeReport.workTimeUtilizationLevel}%</td>
        </tr>
        <tr>
            <th>Is monthly target achieved? (${employeeReport.reportedEmployee.additionalInfo.targetBudget} PLN)</th>
            <td>${employeeReport.isMonthlyTargetAchieved}</td>
        </tr>
        <tr>
            <th>Value of rendered services</th>
            <td>${employeeReport.valueOfRenderedServices} PLN</td>
        </tr>
        <tr>
            <th>Bonus amount</th>
            <td>${employeeReport.bonusAmount} PLN</td>
        </tr>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
