<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<%--todo w widokach zrób if'y ==> dostep dpwoiednio dla usera i admina - trzymaj w sesji typ uprawnien i weryfikuj to wzgledem tego
ALTERNATYWNIE - ZRÓB DWA WIDOKI I W KONTROLERZE W ZALEZNOSCI OD ATRYBUTU - PRZEKIERUJ DO WLASCIWEGO WIDOKU--%>

<h4>Start page - access without logging</h4>
<h4>For full access - please <a href="/login">login</a> </h4>

</body>
</html>
