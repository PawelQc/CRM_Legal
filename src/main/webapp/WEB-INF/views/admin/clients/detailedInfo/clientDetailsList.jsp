<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Client details</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Client: ${client.nameDisplay}</h1>
    <a href="/clients/additional-info/update/${additionalInfoClient.id}"
       class="btn btn-warning rounded-0 text-light m-1">Update</a>
    <table class="table table-hover">
        <tr>
            <th>NIP</th>
            <td>${additionalInfoClient.nip}</td>
        </tr>
        <tr>
            <th>Address</th>
            <td>${additionalInfoClient.address}</td>
        </tr>
        <tr>
            <th>Email</th>
            <td>${additionalInfoClient.email}</td>
        </tr>
        <tr>
            <th>Phone no</th>
            <td>${additionalInfoClient.phoneNumber}</td>
        </tr>
        <tr>
            <th>Charged per hour (PLN)</th>
            <td>${additionalInfoClient.hourlyRateIsCharged}</td>
        </tr>
        <tr>
            <th>Bank account no</th>
            <td>${additionalInfoClient.bankAccount}</td>
        </tr>
    </table>
</div>
<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
