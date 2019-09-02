<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login success</title>
    <%@ include file="/fragments/headStart.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="slider-item overlay backgroundImage2" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row slider-text align-items-center justify-content-center">
            <div class="col-lg-9 text-center col-sm-12 element-animate">
                <h2 class="text-center">Successful logging as user: ${loggedInUser.nameDisplay}</h2>

            </div>
        </div>
    </div>
</div>


<%@ include file="/fragments/footer.jspf" %>
<%@ include file="/fragments/jsCodeStart.jspf" %>

</body>
</html>
