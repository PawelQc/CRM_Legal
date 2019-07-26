<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%@ include file="/fragments/header.jspf" %>

<form:form method="post" modelAttribute="client">
    <label for="legalFormId">Legal form:</label>
    <form:select path="legalPersonality.id" items="${legalPersonality}" itemLabel="legalForm" itemValue="id"
                 id="legalFormId"/>
    <form:errors path="legalPersonality.id" element="div"/>

    <br/><br/>

    <label for="companyNameId">Company name:</label>
    <form:input type="text" path="companyName" id="companyNameId"/>
    <form:errors path="companyName" element="div"/>

    <br/><br/>

    <label for="firstNameId">First name:</label>
    <form:input type="text" path="firstName" id="firstNameId"/>
    <form:errors path="firstName" element="div"/>

    <br/><br/>

    <label for="lastNameId">Last name:</label>
    <form:input type="text" path="lastName" id="lastNameId"/>
    <form:errors path="lastName" element="div"/>

    <br/><br/>

    <label for="nipId">NIP (tax id no):</label>
    <form:input type="number" path="nip" id="nipId"/>
    <form:errors path="nip" element="div"/>

    <br/><br/>

    <label for="assignedEmployeeId">Assigned lawyer:</label>
    <form:select path="assignedEmployee.id" items="${employees}" itemLabel="lastName" itemValue="id"
                 id="assignedEmployeeId"/>
    <form:errors path="assignedEmployee.id" element="div"/>

    <br/><br/>

    <label for="emailId">Email:</label>
    <form:input type="text" path="email" id="emailId"/>
    <form:errors path="email" element="div"/>

    <br/><br/>

    <label for="addressId">Address:</label>
    <form:input type="text" path="address" id="addressId"/>
    <form:errors path="address" element="div"/>

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
