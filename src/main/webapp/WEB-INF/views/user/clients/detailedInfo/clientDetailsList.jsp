<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/headerUser.jspf" %>

<h3>Client - details</h3>

<table border="1">
    <tr>
        <th>NIP no</th>
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
</table>

</body>
</html>
