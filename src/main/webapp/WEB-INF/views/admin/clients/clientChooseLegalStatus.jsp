<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add client</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>
<div class="container">
    <h1 class="text-center">Adding new client: choose client's legal status:</h1>
    <ul class="text-center ul-no-style">
        <li>
            <a href="<c:url value="/clients/legal-person/add"/>" class="btn btn-success rounded-0 text-light m-1 btn-client-add">
                <h4>legal person</h4>
            </a>
        </li>
        <br>
        <li>
            <a href="<c:url value="/clients/natural-person/add"/>" class="btn btn-success rounded-0 text-light m-1 btn-client-add">
                <h4>natural person</h4>
            </a>
        </li>
    </ul>
</div>
<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
