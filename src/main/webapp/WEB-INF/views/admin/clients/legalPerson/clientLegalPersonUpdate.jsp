<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/headerAdmin.jspf" %>

<h4>Update client - legal person (basic info)</h4>

<form:form method="post" modelAttribute="clientLegalPerson" action="/clients/legal-person/update">
    <form:hidden path="id"/>

    <c:if test="${not empty clientLegalPerson.additionalInfo}">
        <form:hidden path="additionalInfo.id"/>
    </c:if>

    <label for="companyNameId">Company name:</label>
    <form:input type="text" path="companyName" id="companyNameId"/>
    <form:errors path="companyName" element="div"/>

    <br/><br/>

    <input type="submit" value="Update">
</form:form>
</body>
</html>
