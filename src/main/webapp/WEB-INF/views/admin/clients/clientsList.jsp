<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/headerAdmin.jspf" %>

<h3>List of all clients</h3>

<a href="/clients/add">Add</a>

<table border="1">
    <tr>
        <th>No</th>
        <th>Company name</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Action</th>
    </tr>
    <c:forEach var="client" items="${clients}" varStatus="count">
        <tr>
            <td>${count.count}</td>
            <td>
                <c:catch var="exception">${client.companyName}</c:catch>
                <c:if test="${not empty exception}"> - </c:if>
            </td>
            <td>
                <c:catch var="exception">${client.firstName}</c:catch>
                <c:if test="${not empty exception}"> - </c:if>
            </td>
            <td>
                <c:catch var="exception">${client.lastName}</c:catch>
                <c:if test="${not empty exception}"> - </c:if>
            </td>
            <td>
                <c:choose>
                    <c:when test="${client['class'].simpleName == 'ClientLegalPerson'}">
                        <a href="/clients/legal-person/update/${client.id}">Update</a>
                    </c:when>
                    <c:otherwise>
                        <a href="/clients/natural-person/update/${client.id}">Update</a>
                    </c:otherwise>
                </c:choose>
                <a href="/clients/additional-info/list?clientId=${client.id}&additionalInfoId=${client.additionalInfo.id}">Details</a>
                <a href="http://localhost:8080/clients/delete/${client.id}"
                   onclick="return confirm('Are you sure you want to delete this client?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
    <h4>${deleteErrorProjectExists}</h4>

</table>

</body>
</html>
