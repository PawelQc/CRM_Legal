<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/headerAdmin.jspf" %>

<h3>Projects</h3>

<table border="1">
    <tr>
        <th>No</th>
        <th>Signature</th>
        <th>Name</th>
        <th>Description</th>
        <th>Team</th>
        <th>Client</th>
    </tr>
    <c:forEach var="project" items="${projects}" varStatus="count">
        <tr>
            <td>${count.count}</td>
            <td>${project.signature}</td>
            <td>${project.name}</td>
            <td>${project.description}</td>
            <td>
                <c:forEach items="${project.projectTeam}" var="lawyer">
                    ${lawyer.lastName}
                </c:forEach>
            </td>
            <td>${project.client.toString()}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
