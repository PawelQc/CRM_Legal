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

<h1>Legal CRM - Welcome</h1>
<h4>Start page - access without logging</h4>
<h4>For full access - please <a href="/login">login</a> </h4>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
