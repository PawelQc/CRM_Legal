<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h3>Timesheets - all</h3>

<a href="/timesheet/choose-case">Add</a>


<c:forEach items="${legalCases}" var="legalCase">
    <h4> Case: ${legalCase.signature}</h4>
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
        <c:forEach var="timesheet" items="${legalCase.timesheets}" varStatus="count">
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
                    <a href="http://localhost:8080/timesheet/delete/${timesheet.id}/${legalCase.id}"
                       onclick="return confirm('Are you sure you want to delete this record?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>

</c:forEach>


</body>
</html>
