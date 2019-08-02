<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Cases</h3>

<a href="/legal-cases/add">Add</a>

<table border="1">
    <tr>
        <th>No</th>
        <th>Signature</th>
        <th>Name</th>
        <th>Description</th>
        <th>Team</th>
        <th>Client</th>
        <th>Action</th>
    </tr>
    <c:forEach var="legalCase" items="${legalCases}" varStatus="count">
        <tr>
            <td>${count.count}</td>
            <td>${legalCase.signature}</td>
            <td>${legalCase.name}</td>
            <td>${legalCase.description}</td>
            <td>
                <c:forEach items="${legalCase.projectTeam}" var="lawyer">
                    ${lawyer.lastName}
                </c:forEach>
            </td>
            <td>${legalCase.client.toString()}</td>
            <td>
                <a href="/legal-cases/update/${legalCase.id}">Update</a>
                <a href="http://localhost:8080/legal-cases/delete/${legalCase.id}"
                   onclick="return confirm('Are you sure you want to delete this case?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
