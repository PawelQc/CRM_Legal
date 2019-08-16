<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>${employee.nameDisplay} - details</h3>

<table border="1">

    <tr>
        <th>Email</th>
        <td>${loggedInUser.emailLogin}</td>
    </tr>
    <tr>
        <th>Hourly rate salary (PLN)</th>
        <td>${additionalInfo.hourlyRateReceivingSalary}</td>
    </tr>
    <tr>
        <th>Hourly rate client charge (PLN)</th>
        <td>${additionalInfo.hourlyRateChargingClients}</td>
    </tr>
    <tr>
        <th>Target budget (monthly)</th>
        <td>${additionalInfo.targetBudget}</td>
    </tr>
    <tr>
        <th>Bonus rate</th>
        <td>${additionalInfo.bonus}%</td>
    </tr>
    <tr>
        <th>Phone no</th>
        <td>${additionalInfo.phoneNumber}</td>
    </tr>
</table>
<a href="/user/info/update/${additionalInfo.id}">Update</a>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
