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

<h3>Project profitability report</h3>
<h4>Choose a project to generate a report</h4>

<h4>${errorNotSufficientData}</h4>

<form action="/reports/project-report/process" method="post">
    <label><h4>Select project</h4>
        <select name="projectId">
            <c:forEach items="${projects}" var="project">
                <option value="${project.id}">${project.signature}</option>
            </c:forEach>
        </select> <br><br>
    </label>

    <input type="submit" value="Generate report">
</form>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
