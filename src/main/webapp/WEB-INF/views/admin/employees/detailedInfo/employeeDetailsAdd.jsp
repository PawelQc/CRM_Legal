<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add employee details</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">No detailed information about ${employee.nameDisplay}: fill the form</h1>
    <form:form method="post" modelAttribute="additionalInfoEmployee">
        <div class="form-group">
            <label for="hourlyRateReceivingSalaryId">Salary in PLN (hourly rate): </label>
            <form:input type="number" path="hourlyRateReceivingSalary" id="hourlyRateReceivingSalaryId" min="1" max="100000"
                        class="form-control"/>
            <form:errors path="hourlyRateReceivingSalary" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="hourlyRateChargingClientsId">Charge rate in PLN (hourly rate): </label>
            <form:input type="number" path="hourlyRateChargingClients" id="hourlyRateChargingClientsId" min="1" max="100000"
                        class="form-control"/>
            <form:errors path="hourlyRateChargingClients" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="targetBudgetId">Target budget in PLN (per month) </label>
            <form:input type="number" path="targetBudget" id="targetBudgetId" min="1" max="1000000" class="form-control"/>
            <form:errors path="targetBudget" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="bonusId">Bonus rate (%) </label>
            <form:input type="number" path="bonus" id="bonusId" min="0" max="100" class="form-control"/>
            <form:errors path="bonus" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="phoneNumberId">Phone no</label>
            <form:input type="number" path="phoneNumber" id="phoneNumberId" class="form-control"/>
            <form:errors path="phoneNumber" element="div" cssClass="text-warning"/>
        </div>
        <input type="submit" value="Save">
    </form:form>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
