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

<form:form method="post" modelAttribute="additionalInfoEmployee" action="/employees/additional-info/update">
    <form:hidden path="id"/>

    <label for="hourlyRateReceivingSalaryId">Salary in PLN (hourly rate): </label>
    <form:input type="number" path="hourlyRateReceivingSalary" id="hourlyRateReceivingSalaryId"  min="1"/>
    <form:errors path="hourlyRateReceivingSalary" element="div"/>

    <br/><br/>

    <label for="hourlyRateChargingClientsId">Charge rate in PLN (hourly rate): </label>
    <form:input type="number" path="hourlyRateChargingClients" id="hourlyRateChargingClientsId"  min="1"/>
    <form:errors path="hourlyRateChargingClients" element="div"/>

    <br/><br/>

    <label for="targetBudgetId">Target budget in PLN (per month) </label>
    <form:input type="number" path="targetBudget" id="targetBudgetId"  min="1"/>
    <form:errors path="targetBudget" element="div"/>

    <br/><br/>

    <label for="bonusId">Bonus rate (%) </label>
    <form:input type="number" path="bonus" id="bonusId" min="0"/>
    <form:errors path="bonus" element="div"/>

    <br/><br/>

    <label for="phoneNumberId">Phone no</label>
    <form:input type="number" path="phoneNumber" id="phoneNumberId"/>
    <form:errors path="phoneNumber" element="div"/>

    <br/><br/>


    <input type="submit" value="Save">
</form:form>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
