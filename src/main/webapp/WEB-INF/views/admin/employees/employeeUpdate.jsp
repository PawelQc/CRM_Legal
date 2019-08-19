<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update employee</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Update employee (basic info): ${employee.nameDisplay}</h1>
    <form:form method="post" modelAttribute="employee" action="/employees/update">
        <form:hidden path="id" value="${employee.id}"/>
        <form:hidden path="password" value="${employee.password}"/>
        <c:if test="${not empty employee.additionalInfo}">
            <form:hidden path="additionalInfo.id"/>
        </c:if>
        <div class="form-group">
            <label for="firstNameId">First name:</label>
            <form:input type="text" path="firstName" id="firstNameId" class="form-control"/>
            <form:errors path="firstName" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="lastNameId">Last name:</label>
            <form:input type="text" path="lastName" id="lastNameId" class="form-control"/>
            <form:errors path="lastName" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="emailLoginId">Email (login):</label>
            <form:input type="text" path="emailLogin" id="emailLoginId" class="form-control"/>
            <form:errors path="emailLogin" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="isAdminId">Grant admin access?</label>
            <form:checkbox path="admin" id="isAdminId" class="form-control"/>
            <form:errors path="admin" element="div" cssClass="text-warning"/>
        </div>

        <input type="submit" value="Save">
    </form:form>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
