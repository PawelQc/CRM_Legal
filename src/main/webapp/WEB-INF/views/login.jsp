<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<header class="w3-container w3-red w3-center" style="padding:128px 16px">
    <div class="container">
        <h1>Login</h1>
        <h4 class="text-info"> ${SPRING_SECURITY_LAST_EXCEPTION.message} </h4>
        <form action="login" method='post'>
            <div class="form-group">
                <label for="email">Email address:</label>
                <input type="email" class="form-control" id="email" name='username'>
            </div>
            <div class="form-group">
                <label for="pwd">Password:</label>
                <input type="password" class="form-control" id="pwd" name='password'>
            </div>
            <button type="submit" class="btn btn-warning btn-lg rounded-0 text-light m-1">Submit</button>
        </form>
    </div>
</header>



<%@ include file="/fragments/footer.jspf" %>
</body>
</html>