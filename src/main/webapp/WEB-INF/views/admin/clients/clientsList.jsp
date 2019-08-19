<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Clients</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Clients</h1>
    <a href="/clients/add" class="btn btn-success rounded-0 text-light m-1">Add client</a>
    <table class="table table-hover">
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
                            <a href="/clients/legal-person/update/${client.id}"
                               class="btn btn-warning rounded-0 text-light m-1">Update</a>
                        </c:when>
                        <c:otherwise>
                            <a href="/clients/natural-person/update/${client.id}"
                               class="btn btn-warning rounded-0 text-light m-1">Update</a>
                        </c:otherwise>
                    </c:choose>
                    <a href="/clients/additional-info/list?clientId=${client.id}&additionalInfoId=${client.additionalInfo.id}"
                       class="btn btn-info rounded-0 text-light m-1">Details</a>
                    <a href="http://localhost:8080/clients/delete/${client.id}"
                       class="btn btn-danger rounded-0 text-light m-1"
                       onclick="return confirm('Are you sure you want to delete this client?'); ">Delete</a>
                </td>
            </tr>
        </c:forEach>
        <h4 class="text-warning">${deleteErrorProjectExists}</h4>
    </table>
</div>
<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
