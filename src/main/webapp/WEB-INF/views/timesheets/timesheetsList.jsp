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
        <th>MMinutes</th>
        <th>TMinutes</th>
        <th>WMinutes</th>
        <th>ThMinutes</th>
        <th>FMinutes</th>
        <th>SatMinutes</th>
        <th>SunMinutes</th>
        <th>Action</th>
    </tr>
    <c:forEach var="timesheet" items="${timesheets}" varStatus="count">
        <tr>
            <td>${count.count}</td>
            <td>${timesheet.dateMonday}</td>
            <td>${timesheet.mondayMinutes}</td>
            <td>${timesheet.tuesdayMinutes}</td>
            <td>${timesheet.wednesdayMinutes}</td>
            <td>${timesheet.thursdayMinutes}</td>
            <td>${timesheet.fridayMinutes}</td>
            <td>${timesheet.saturdayMinutes}</td>
            <td>${timesheet.sundayMinutes}</td>
            <td>
                    <a href="/timesheet/update/${timesheet.id}">Update</a>
                    <a href="/timesheet/delete/${timesheet.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
