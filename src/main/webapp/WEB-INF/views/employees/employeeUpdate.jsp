<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<form:form method="post" modelAttribute="employee" action="/employees/update">

    <form:hidden path="id" value="${employee.id}"/>

    <c:if test="${not empty employee.additionalInfo}">
        <form:hidden path="additionalInfo.id"/>
    </c:if>

    <label for="firstNameId">First name:</label>
    <form:input type="text" path="firstName" id="firstNameId"/>
    <form:errors path="firstName" element="div"/>

    <br/><br/>

    <label for="lastNameId">Last name:</label>
    <form:input type="text" path="lastName" id="lastNameId"/>
    <form:errors path="lastName" element="div"/>

    <br/><br/>

    <label for="emailLoginId">Email (login):</label>
    <form:input type="text" path="emailLogin" id="emailLoginId"/>
    <form:errors path="emailLogin" element="div"/>

    <br/><br/>

    <label for="passwordId">Password:</label>
    <form:input type="password" path="password" id="passwordId"/>
    <form:errors path="password" element="div"/>

    <br/><br/>

    <label for="isAdminId">Grant admin access?</label>
    <form:checkbox path="admin" id="isAdminId"/>
    <form:errors path="admin" element="div"/>

    <br/><br/>

    <input type="submit" value="Save">
</form:form>
</body>
</html>
