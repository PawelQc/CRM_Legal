<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Employees</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Employees</h1>
    <table class="table table-hover">
        <tr>
            <th>No</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Email</th>
            <th>Action</th>
        </tr>
        <c:forEach var="employee" items="${employees}" varStatus="count">
            <tr>
                <td>${count.count}</td>
                <td>${employee.firstName}</td>
                <td>${employee.lastName}</td>
                <td>${employee.emailLogin}</td>
                <td>
                    <a href="/employees/additional-info/list?employeeId=${employee.id}&additionalInfoId=${employee.additionalInfo.id}"
                       class="btn btn-info rounded-0 text-light m-1">Details</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
