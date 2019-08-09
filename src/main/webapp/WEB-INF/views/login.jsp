<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1>Login</h1>

<div class="panel-body">
    <form action="doLogin" method="post">
        <fieldset>
            <legend>Please sign in</legend>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                        ${SPRING_SECURITY_LAST_EXCEPTION.message}
                    <br/>
                </div>
            </c:if>
            <div class="form-group">
                <input class="form:input-large" placeholder="User Name"
                       name='username' type="text">
            </div>
            <div class="form-group">
                <input class=" form:input-large" placeholder="Password"
                       name='password' type="password" value="">
            </div>
            <input class="btn" type="submit"
                   value="Login">
        </fieldset>
    </form>
</div>
</body>
</html>