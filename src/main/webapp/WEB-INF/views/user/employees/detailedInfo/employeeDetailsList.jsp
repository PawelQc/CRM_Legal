<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Employee details</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Employee: ${employee.nameDisplay}</h1>
    <table class="table table-hover">
        <tr>
            <th>Hourly rate client charge (PLN)</th>
            <td>${additionalInfoEmployee.hourlyRateChargingClients}</td>
        </tr>
        <tr>
            <th>Phone no</th>
            <td>${additionalInfoEmployee.phoneNumber}</td>
        </tr>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
