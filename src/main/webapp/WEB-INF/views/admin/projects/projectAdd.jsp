<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add project</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Add project</h1>

    <form:form method="post" modelAttribute="project">
        <div class="form-group">
            <label for="signatureId">Signature:</label>
            <form:input type="text" path="signature" id="signatureId" class="form-control"/>
            <form:errors path="signature" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="nameId">Name:</label>
            <form:input type="text" path="name" id="nameId" class="form-control"/>
            <form:errors path="name" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="clientId">Client:</label>
            <form:select path="client.id" class="form-control">
                <form:option value="${null}" label="--Please select a client--" id="clientId"/>
                <c:forEach items="${clients}" var="client">
                    <form:option value="${client.id}" label="${client.nameDisplay}" id="clientId"/>
                </c:forEach>
            </form:select>
            <form:errors path="client.id" cssClass="text-warning"/>
            <div class="text-warning">${errorClientChoice}</div>
        </div>

        <div class="form-group">
            <label for="capOnRemunerationId">Cap on remuneration:</label>
            <form:input type="number" min="0" max="100000000" path="capOnRemuneration" id="capOnRemunerationId"
                        class="form-control"/>
            <form:errors path="capOnRemuneration" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="descriptionId">Description:</label>
            <form:textarea path="description" id="descriptionId" class="form-control txt-area-no-resize"
                           maxlength="200"/>
            <form:errors path="description" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="teamId">Project team:</label>
            <form:select path="projectTeam" multiple="true" class="form-control">
                <c:forEach items="${employees}" var="lawyer">
                    <form:option value="${lawyer.id}" label="${lawyer.nameDisplay}" id="teamId"/>
                </c:forEach>
            </form:select>
            <form:errors path="projectTeam" element="div" cssClass="text-warning"/>
        </div>

        <div class="form-group">
            <label for="isBillableId">Is project billable?</label>
            <form:checkbox path="isBillable" id="isBillableId" class="form-control"/>
            <form:errors path="isBillable" element="div" cssClass="text-warning"/>
        </div>

        <input type="submit" value="Save" class="btn-success">
    </form:form>
</div>
<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
