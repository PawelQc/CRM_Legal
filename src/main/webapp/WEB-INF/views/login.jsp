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
                <h2>Login</h2>
                <h4 class="text-warning"> ${SPRING_SECURITY_LAST_EXCEPTION.message} </h4>
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
        </div>
    </div>
</div>


<%@ include file="/fragments/footer.jspf" %>
<%@ include file="/fragments/jsCodeStart.jspf" %>

</body>
</html>