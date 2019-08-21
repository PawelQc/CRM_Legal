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
    <h1 class="text-center">Changing password of ${employee.nameDisplay}</h1>
    <form action="/employees/update-password/${employee.id}" method="post">
        <div class="form-group">
            <label for="passwordId">Enter a new password:</label>
            <input type="password" name="password" class="form-control" id="passwordId">
        </div>
        <p class="text-warning"> ${errorPasswordInput}</p>
        <input type="submit" value="Update">
    </form>
</div>
<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
