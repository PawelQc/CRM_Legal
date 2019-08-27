<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Project choice</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Choose project to add work time</h1>

    <table class="table table-hover">
        <tr>
            <th>No</th>
            <th>Signature</th>
            <th>Name</th>
            <th>Client</th>
            <th>Action</th>
        </tr>
        <c:forEach var="project" items="${projectsUser}" varStatus="count">
            <tr>
                <td>${count.count}</td>
                <td>${project.signature}</td>
                <td>${project.name}</td>
                <td>${project.client.nameDisplay}</td>
                <td>
                    <a href="/timesheets/add/${project.id}" class="btn btn-success rounded-0 text-light m-1">Add
                        timesheet</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
