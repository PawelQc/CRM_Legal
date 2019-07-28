<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<h4>Add timesheet (indicate time in hours and quarters)</h4>

<form:form method="post" modelAttribute="timesheetWeek">
    <form:hidden path="dateMonday"/>

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
                <label for="mondayMinutesId">Time in hours (0,25-24):</label>
                <form:input type="number" path="mondayMinutes" id="mondayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="mondayMinutes"/>
            </td>
            <td>
                <label for="tuesdayMinutesId">Time in hours (0,25-24):</label>
                <form:input type="number" path="tuesdayMinutes" id="tuesdayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="tuesdayMinutes"/>
            </td>
            <td>
                <label for="wednesdayMinutesId">Time in hours (0,25-24):</label>
                <form:input type="number" path="wednesdayMinutes" id="wednesdayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="wednesdayMinutes"/>
            </td>
            <td>
                <label for="thursdayMinutesId">Time in hours (0,25-24):</label>
                <form:input type="number" path="thursdayMinutes" id="thursdayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="thursdayMinutes"/>
            </td>
            <td>
                <label for="fridayMinutesId">Time in hours (0,25-24):</label>
                <form:input type="number" path="fridayMinutes" id="fridayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="fridayMinutes"/>
            </td>
            <td>
                <label for="saturdayMinutesId">Time in hours (0,25-24):</label>
                <form:input type="number" path="saturdayMinutes" id="saturdayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="saturdayMinutes"/>
            </td>
            <td>
                <label for="sundayMinutesId">Time in hours (0,25-24):</label>
                <form:input type="number" path="sundayMinutes" id="sundayMinutesId" step="1" min="1" max="24"/>
                <form:errors path="sundayMinutes"/>
            </td>
        </tr>

    </table>

    <br/>
    <input type="submit" value="Save">
</form:form>
</body>
</html>
