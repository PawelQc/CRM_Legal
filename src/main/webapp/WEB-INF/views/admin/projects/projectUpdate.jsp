<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<form:form method="post" modelAttribute="project" action="/projects/update">

    <form:hidden path="id" value="${project.id}"/>

    <label for="signatureId">Signature:</label>
    <form:input type="text" path="signature" id="signatureId"/>
    <form:errors path="signature" element="div"/>

    <br/><br/>

    <label for="nameId">Name:</label>
    <form:input type="text" path="name" id="nameId"/>
    <form:errors path="name" element="div"/>

    <br/><br/>

    <label for="clientId">Client:</label>
    <form:select path="client.id">
        <form:option value="${null}" label="--Please select a client--" id="clientId"/>
        <c:forEach items="${clients}" var="client">
            <form:option value="${client.id}" label="${client.toString()}" id="clientId"/>
        </c:forEach>
    </form:select>
    <form:errors path="client.id"/>
    <div>${errorClientChoice}</div>

    <br/><br/>

    <label for="capOnRemunerationId">Cap on remuneration:</label>
    <form:input type="number" min="0" path="capOnRemuneration" id="capOnRemunerationId"/>
    <form:errors path="capOnRemuneration" element="div"/>

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
    <form:errors path="projectTeam" element="div"/>

    <br/><br/>

    <label for="isBillableId">Is project billable?</label>
    <form:checkbox path="billable" id="isBillableId"/>
    <form:errors path="billable" element="div"/>

    <br/><br/>

    <input type="submit" value="Save">
</form:form>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
