<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add client info</title>
    <%@ include file="/fragments/head.jspf" %>

</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">No detailed information about ${client.nameDisplay}: fill the form</h1>
    <form:form method="post" modelAttribute="additionalInfoClient">
        <div class="form-group">
            <label for="nipId">NIP:</label>
            <form:input type="number" path="nip" id="nipId" class="form-control"/>
            <form:errors path="nip" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="addressId">Address:</label>
            <form:input type="text" path="address" id="addressId" class="form-control"/>
            <form:errors path="address" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="emailId">Email:</label>
            <form:input type="text" path="email" id="emailId" class="form-control"/>
            <form:errors path="email" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="phoneId">Phone no:</label>
            <form:input type="number" path="phoneNumber" id="phoneId" class="form-control"/>
            <form:errors path="phoneNumber" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="bankAccountId">Bank account no:</label>
            <form:input type="number" path="bankAccount" id="bankAccountId" class="form-control"/>
            <form:errors path="bankAccount" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="hourlyRateId">Hourly rate:</label>
            <form:input type="number" path="hourlyRateIsCharged" id="hourlyRateId" class="form-control" min="1"
                        max="100000"/>
            <form:errors path="hourlyRateIsCharged" element="div" cssClass="text-warning"/>
        </div>

        <input type="submit" value="Save">
    </form:form>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
