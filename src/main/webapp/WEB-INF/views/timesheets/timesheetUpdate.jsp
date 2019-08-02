<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%@ include file="/fragments/header.jspf" %>

<h4>Edit timesheet (indicate time in full hours)</h4>

<form:form method="post" modelAttribute="timesheetWeek" action="/timesheet/update">
    <form:hidden path="dateMonday"/>
    <form:hidden path="id"/>
    <table border="1">
        <tr>
            <th>${timesheetWeek.dateMonday}</th>
            <th>${timesheetWeek.dateMonday.plusDays(1)}</th>
            <th>${timesheetWeek.dateMonday.plusDays(2)}</th>
            <th>${timesheetWeek.dateMonday.plusDays(3)}</th>
            <th>${timesheetWeek.dateMonday.plusDays(4)}</th>
            <th>${timesheetWeek.dateMonday.plusDays(5)}</th>
            <th>${timesheetWeek.dateMonday.plusDays(6)}</th>
        </tr>
        <tr>
            <td>
                <label for="mondayMinutesId">Time in hours (1-24):</label>
                <form:input type="number" path="mondayHours" id="mondayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="mondayHours"/>
            </td>
            <td>
                <label for="tuesdayMinutesId">Time in hours (1-24):</label>
                <form:input type="number" path="tuesdayHours" id="tuesdayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="tuesdayHours"/>
            </td>
            <td>
                <label for="wednesdayMinutesId">Time in hours (1-24):</label>
                <form:input type="number" path="wednesdayHours" id="wednesdayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="wednesdayHours"/>
            </td>
            <td>
                <label for="thursdayMinutesId">Time in hours (1-24):</label>
                <form:input type="number" path="thursdayHours" id="thursdayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="thursdayHours"/>
            </td>
            <td>
                <label for="fridayMinutesId">Time in hours (1-24):</label>
                <form:input type="number" path="fridayHours" id="fridayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="fridayHours"/>
            </td>
            <td>
                <label for="saturdayMinutesId">Time in hours (1-24):</label>
                <form:input type="number" path="saturdayHours" id="saturdayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="saturdayHours"/>
            </td>
            <td>
                <label for="sundayMinutesId">Time in hours (1-24):</label>
                <form:input type="number" path="sundayHours" id="sundayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="sundayHours"/>
            </td>
        </tr>

    </table>

    <br/>
    <input type="submit" value="Save">
</form:form>
</body>
</html>
