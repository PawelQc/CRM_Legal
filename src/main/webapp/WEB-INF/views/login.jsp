<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
    <%@ include file="/fragments/headStart.jspf" %>
</head>
<body>

<div class="slider-item overlay" data-stellar-background-ratio="0.5"
     style="background-image: url('/resources/images/start.jpg');">
    <div class="container">
        <div class="row slider-text align-items-center justify-content-center">
            <div class="col-lg-9 text-center col-sm-12 element-animate">
                <a href="/" class="btn btn-success rounded-0 text-light m-1"><h3>Go back to homepage</h3></a> <br><br><br>

                <h1>Login</h1>
                <h3 class="text-warning">
                    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                            Your login attempt was not successful due to <br/><br/>
                            <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
                    </c:if>
                </h3>
                <form action="login" method='post'>
                    <div class="form-group">
                        <label for="email"><h4>Email address:</h4></label>
                        <input type="email" class="form-control" id="email" name='username'>
                    </div>
                    <div class="form-group">
                        <label for="pwd"><h4>Password:</h4></label>
                        <input type="password" class="form-control" id="pwd" name='password'>
                    </div>
                    <button type="submit" class="btn btn-warning btn-lg rounded-0 text-light m-1">Submit</button>
                </form>
            </div>
        </div>
    </div>
</div>


<%@ include file="/fragments/footer.jspf" %>
<%@ include file="/fragments/jsCodeStart.jspf" %>

</body>
</html>