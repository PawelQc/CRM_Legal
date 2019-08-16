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

<h3>Employees</h3>

<a href="/employees/add">Add</a>

<table border="1">
    <tr>
        <th>No</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Email</th>
        <th>Password</th>
        <th>IsAdmin?</th>
        <th>Action</th>
    </tr>
    <c:forEach var="employee" items="${employees}" varStatus="count">
        <tr>
            <td>${count.count}</td>
            <td>${employee.firstName}</td>
            <td>${employee.lastName}</td>
            <td>${employee.emailLogin}</td>
            <td>${employee.password}</td>
            <td>${employee.admin}</td>
            <td>
                <a href="/employees/update/${employee.id}">Update</a>
                <a href="/employees/update-password/${employee.id}">Change password</a>
                <a href="/employees/additional-info/list?employeeId=${employee.id}&additionalInfoId=${employee.additionalInfo.id}">Details</a>
                <a href="http://localhost:8080/employees/delete/${employee.id}"
                   onclick="return confirm('Are you sure you want to delete this employee? All his timesheets will be deleted!');">Delete</a>
            </td>
        </tr>
    </c:forEach>
    <h4>${deleteErrorProjectExists}</h4> <h4>${deleteErrorAdmin}</h4>
</table>


<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
