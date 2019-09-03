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
    <h1 class="text-center">Add client: legal person (basic info)</h1>

    <form:form method="post" modelAttribute="clientLegalPerson">
        <div class="form-group">
            <label for="companyNameId">Company name:</label>
            <form:input type="text" path="companyName" id="companyNameId" class="form-control"/>
            <form:errors path="companyName" element="div" cssClass="text-warning"/>
        </div>
        <input type="submit" value="Save" class="btn-success">
    </form:form>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
