<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/headerAdmin.jspf" %>

<form:form method="post" modelAttribute="additionalInfoEmployee" action="/employees/additional-info/update">
    <form:hidden path="id"/>

    <label for="hourlyRateReceivingSalaryId">Salary (hourly rate): </label>
    <form:input type="number" path="hourlyRateReceivingSalary" id="hourlyRateReceivingSalaryId"/>
    <form:errors path="hourlyRateReceivingSalary" element="div"/>

    <br/><br/>

    <label for="hourlyRateChargingClientsId">Charge rate (hourly rate): </label>
    <form:input type="number" path="hourlyRateChargingClients" id="hourlyRateChargingClientsId"/>
    <form:errors path="hourlyRateChargingClients" element="div"/>

    <br/><br/>

    <label for="targetBudgetId">Target budget (per month) </label>
    <form:input type="number" path="targetBudget" id="targetBudgetId"/>
    <form:errors path="targetBudget" element="div"/>

    <br/><br/>

    <label for="phoneNumberId">Phone no</label>
    <form:input type="number" path="phoneNumber" id="phoneNumberId"/>
    <form:errors path="phoneNumber" element="div"/>

    <br/><br/>


    <input type="submit" value="Save">
</form:form>
</body>
</html>
