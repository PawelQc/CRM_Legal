<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<form:form method="post" modelAttribute="employee" action="/employees/update">

    <form:hidden path="id" value="${employee.id}"/>

    <label for="firstNameId">First name:</label>
    <form:input type="text" path="firstName" id="firstNameId"/>
    <form:errors path="firstName"/>

    <br/><br/>

    <label for="lastNameId">Last name:</label>
    <form:input type="text" path="lastName" id="lastNameId"/>
    <form:errors path="lastName"/>

    <br/><br/>

    <label for="emailLoginId">Email (login):</label>
    <form:input type="text" path="emailLogin" id="emailLoginId"/>
    <form:errors path="emailLogin"/>

    <br/><br/>

    <label for="hourlyRateReceivingSalaryId">Salary (hourly rate): </label>
    <form:input type="number" path="hourlyRateReceivingSalary" id="hourlyRateReceivingSalaryId"/>
    <form:errors path="hourlyRateReceivingSalary"/>

    <br/><br/>

    <label for="hourlyRateChargingClientsId">Charge rate (hourly rate): </label>
    <form:input type="number" path="hourlyRateChargingClients" id="hourlyRateChargingClientsId"/>
    <form:errors path="hourlyRateChargingClients"/>

    <br/><br/>

    <label for="targetBudgetId">Target budget (per month) </label>
    <form:input type="number" path="targetBudget" id="targetBudgetId"/>
    <form:errors path="targetBudget"/>

    <br/><br/>

    <label for="phoneNumberId">Phone no</label>
    <form:input type="number" path="phoneNumber" id="phoneNumberId"/>
    <form:errors path="phoneNumber"/>

    <br/><br/>

    <input type="submit" value="Save">
</form:form>
</body>
</html>
