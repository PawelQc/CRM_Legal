<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Projects</h3>

<a href="/projects/add">Add</a>

<table border="1">
    <tr>
        <th>No</th>
        <th>Signature</th>
        <th>Name</th>
        <th>Remuneration cap</th>
        <th>Description</th>
        <th>Team</th>
        <th>Client</th>
        <th>Action</th>
    </tr>
    <c:forEach var="project" items="${projects}" varStatus="count">
        <tr>
            <td>${count.count}</td>
            <td>${project.signature}</td>
            <td>${project.name}</td>
            <td>${project.capOnRemuneration}</td>
            <td>${project.description}</td>
            <td>
                <c:forEach items="${project.projectTeam}" var="lawyer">
                    ${lawyer.lastName}
                </c:forEach>
            </td>
            <td>${project.client.toString()}</td>
            <td>
                <a href="/projects/update/${project.id}">Update</a>
                <a href="http://localhost:8080/projects/delete/${project.id}"
                   onclick="return confirm('Are you sure you want to delete this project?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
