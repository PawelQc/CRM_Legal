<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add client</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Add client: natural person (basic info)</h1>
    <form:form method="post" modelAttribute="clientNaturalPerson">
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
        <input type="submit" value="Save" class="btn-success">
    </form:form>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
