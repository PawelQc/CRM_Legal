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
    <h1 class="text-center">Monthly employee report for: ${reportedEmployee.nameDisplay}</h1>
    <h3 class="text-center">Time interval: ${selectedMonday} - ${selectedMonday.plusDays(27)}</h3>
    <table class="table table-hover">
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
            <th>Is monthly target achieved? (${reportedEmployee.additionalInfo.targetBudget} PLN)</th>
            <td>${isMonthlyTargetAchieved}</td>
        </tr>
        <tr>
            <th>Value of rendered services</th>
            <td>${valueOfRenderedServices} PLN</td>
        </tr>
        <tr>
            <th>Bonus amount</th>
            <td>${bonusAmount} PLN</td>
        </tr>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
