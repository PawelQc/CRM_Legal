<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Projects</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Projects</h1>
    <a href="/projects/add" class="btn btn-success rounded-0 text-light m-1">Add</a>
    <table class="table table-hover">
        <tr>
            <th>No</th>
            <th>Signature</th>
            <th>Name</th>
            <th>Description</th>
            <th>Team</th>
            <th>Client</th>
            <th>Remuneration cap</th>
            <th>Action</th>
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
                <td>${project.client.nameDisplay}</td>
                <td>${project.capOnRemuneration}</td>
                <td>
                    <a href="/projects/update/${project.id}" class="btn btn-warning rounded-0 text-light m-1">Update</a>
                    <a href="http://localhost:8080/projects/delete/${project.id}"
                       class="btn btn-danger rounded-0 text-light m-1"
                       onclick="return confirm('Are you sure you want to delete this project?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
