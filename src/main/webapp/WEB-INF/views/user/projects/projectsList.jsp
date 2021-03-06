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
    <table class="table table-hover table-condensed">
        <tr>
            <th>No</th>
            <th>Signature</th>
            <th>Name</th>
            <th>Description</th>
            <th>Team</th>
            <th>Client</th>
            <th>Remuneration limit</th>
        </tr>
        <c:forEach var="project" items="${projects}" varStatus="count">
            <tr>
                <td>${count.count}</td>
                <td>${project.signature}</td>
                <td>${project.name}</td>
                <td>${project.description}</td>
                <td>
                    <ul>
                        <c:forEach items="${project.projectTeam}" var="lawyer">
                            <li>${lawyer.lastName}</li>
                        </c:forEach>
                    </ul>
                </td>
                <td>${project.client.nameDisplay}</td>
                <td>${project.capOnRemuneration}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
