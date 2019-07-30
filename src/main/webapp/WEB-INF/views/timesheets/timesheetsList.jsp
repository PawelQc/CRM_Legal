<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Timesheets</h3>

<a href="/timesheet/add">Add</a>

<table border="1">
    <tr>
        <th>No</th>
        <th>Monday</th>
        <th>MonHours</th>
        <th>TueHours</th>
        <th>WedHours</th>
        <th>ThHours</th>
        <th>FriHours</th>
        <th>SatHours</th>
        <th>SunHours</th>
        <th>Action</th>
    </tr>
    <c:forEach var="timesheet" items="${timesheets}" varStatus="count">
        <tr>
            <td>${count.count}</td>
            <td>${timesheet.dateMonday}</td>
            <td>${timesheet.mondayHours}</td>
            <td>${timesheet.tuesdayHours}</td>
            <td>${timesheet.wednesdayHours}</td>
            <td>${timesheet.thursdayHours}</td>
            <td>${timesheet.fridayHours}</td>
            <td>${timesheet.saturdayHours}</td>
            <td>${timesheet.sundayHours}</td>
            <td>
                    <a href="/timesheet/update/${timesheet.id}">Update</a>
                    <a href="/timesheet/delete/${timesheet.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
