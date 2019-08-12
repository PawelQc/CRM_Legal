<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Employee - details</h3>

<table border="1">
    <tr>
        <th>Hourly rate client charge (PLN)</th>
        <td>${additionalInfoEmployee.hourlyRateChargingClients}</td>
    </tr>
    <tr>
        <th>Phone no</th>
        <td>${additionalInfoEmployee.phoneNumber}</td>
    </tr>
</table>

</body>
</html>
