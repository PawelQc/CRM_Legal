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

<h3>Changing password of employee ${employee.nameDisplay}</h3>

${errorPasswordInput}
<form action="/employees/update-password/${employee.id}" method="post">
    <label><h4>Enter a new password</h4>
        <input type="password" name="password">
    </label>
    <br> <br>
    <input type="submit" value="Update">
</form>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
