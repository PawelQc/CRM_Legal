<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%@ include file="/fragments/header.jspf" %>

<form:form method="post" modelAttribute="legalCase">
    <label for="signatureId">Signature:</label>
    <form:input type="text" path="signature" id="signatureId"/>
    <form:errors path="signature" element="div"/>

    <br/><br/>

    <label for="nameId">Name:</label>
    <form:input type="text" path="name" id="nameId"/>
    <form:errors path="name" element="div"/>

    <br/><br/>

    <label for="clientId">Client:</label>
    <form:select path="client.id" items="${clients}" itemLabel="companyName" itemValue="id"
                 id="clientId"/>
    <form:errors path="client.id" element="div"/>

    <br/><br/>

    <label for="descriptionId">Description:</label>
    <form:textarea path="description" id="descriptionId"/>
    <form:errors path="description" element="div"/>

    <br/><br/>

    <label for="teamId">Project team:</label>
    <form:select path="projectTeam" multiple="true">
        <c:forEach items="${employees}" var="lawyer">
            <form:option value="${lawyer.id}" label="${lawyer.lastName}" id="teamId"/>
        </c:forEach>
    </form:select>
    <form:errors path="projectTeam"/>

    <br/><br/>

    <input type="submit" value="Save">
</form:form>
</body>
</html>
