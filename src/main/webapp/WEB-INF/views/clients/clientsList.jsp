<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Clients</h3>

<a href="/clients/add">Add</a>

<table border="1">
    <tr>
        <th>No</th>
        <th>Legal status</th>
        <th>Company name</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Assigned lawyer</th>
        <th>NIP no</th>
        <th>Email</th>
        <th>Phone no</th>
        <th>Address</th>
        <th>Hourly rate</th>
        <th>Bank account</th>
        <th>Action</th>
    </tr>
    <c:forEach var="client" items="${clients}" varStatus="count">
        <tr>
            <td>${count.count}</td>
            <td>${client.legalPersonality.legalForm}</td>
            <td>${client.companyName}</td>
            <td>${client.firstName}</td>
            <td>${client.lastName}</td>
            <td>${client.assignedEmployee.firstName} ${client.assignedEmployee.lastName}</td>
            <td>${client.nip}</td>
            <td>${client.email}</td>
            <td>${client.phoneNumber}</td>
            <td>${client.address}</td>
            <td>${client.hourlyRateIsCharged}</td>
            <td>${client.bankAccount}</td>
            <td>
                    <a href="/clients/update/${client.id}">Update</a>
                    <a href="/clients/delete/${client.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
