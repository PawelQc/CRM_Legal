<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h4>Update client - natural person (basic info)</h4>

<form:form method="post" modelAttribute="clientNaturalPerson" action="/clients/natural-person/update">
    <form:hidden path="id"/>
    <form:hidden path="additionalInfo.id"/>

    <label for="firstNameId">First name:</label>
    <form:input type="text" path="firstName" id="firstNameId"/>
    <form:errors path="firstName" element="div"/>

    <br/><br/>

    <label for="lastNameId">Last name:</label>
    <form:input type="text" path="lastName" id="lastNameId"/>
    <form:errors path="lastName" element="div"/>

    <br/><br/>

    <input type="submit" value="Update">
</form:form>
</body>
</html>
