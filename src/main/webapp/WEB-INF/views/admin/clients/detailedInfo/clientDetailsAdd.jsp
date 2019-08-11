<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h4>No client detailed information - please fill the form:</h4>

<%@ include file="/fragments/headerAdmin.jspf" %>

<form:form method="post" modelAttribute="additionalInfoClient">

    <label for="nipId">NIP no:</label>
    <form:input type="number" path="nip" id="nipId"/>
    <form:errors path="nip" element="div"/>

    <br/><br/>

    <label for="addressId">Address:</label>
    <form:input type="text" path="address" id="addressId"/>
    <form:errors path="address" element="div"/>

    <br/><br/>

    <label for="emailId">Email:</label>
    <form:input type="text" path="email" id="emailId"/>
    <form:errors path="email" element="div"/>

    <br/><br/>

    <label for="phoneId">Phone no:</label>
    <form:input type="number" path="phoneNumber" id="phoneId"/>
    <form:errors path="phoneNumber" element="div"/>

    <br/><br/>

    <label for="bankAccountId">Bank account no:</label>
    <form:input type="number" path="bankAccount" id="bankAccountId"/>
    <form:errors path="bankAccount" element="div"/>

    <br/><br/>

    <label for="hourlyRateId">Hourly rate:</label>
    <form:input type="number" path="hourlyRateIsCharged" id="hourlyRateId"/>
    <form:errors path="hourlyRateIsCharged" element="div"/>

    <br/><br/>

    <input type="submit" value="Save">
</form:form>
</body>
</html>
