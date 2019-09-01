<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Excel export</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h4 class="text-center text-warning">${excelError}</h4>
    <c:if test="${empty excelError}" >
        <h4 class="text-center">${excelSuccess}</h4>
    </c:if>
</div>
<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
