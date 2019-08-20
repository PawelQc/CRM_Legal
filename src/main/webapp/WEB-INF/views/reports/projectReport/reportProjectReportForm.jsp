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

<div class="container">
    <h1 class="text-center">Project profitability report</h1>
    <h4 class="text-center">Choose a project to generate a report</h4>
    <p class="text-warning">${errorNotSufficientData}</p>

    <form action="/reports/project-report/process" method="post">
        <div class="form-group">
            <label for="projectId">Select project:</label>
            <select name="projectId" class="form-control">
                <c:forEach items="${projects}" var="project">
                    <option value="${project.id}" id="projectId">${project.signature}</option>
                </c:forEach>
            </select>
        </div>
        <input type="submit" value="Generate report">
    </form>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
