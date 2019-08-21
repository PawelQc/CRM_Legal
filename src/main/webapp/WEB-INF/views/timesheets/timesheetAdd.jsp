<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add timesheet</title>
    <%@ include file="/fragments/head.jspf" %>
    <style>
        a {
            text-decoration: none;
        }
    </style>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<div class="container">
    <h1 class="text-center">Add timesheet</h1>
    <h4 class="text-warning" style="display: inline-block">${errorSimilarTsExists}</h4>
    <c:if test="${not empty timesheetSimilarInDB}">
        <a href="/timesheets/details/${timesheetSimilarInDB.timesheetWeek.id}"
           class="btn btn-danger rounded-0 text-light m-1">check</a> <br> <br>
    </c:if>

    <form:form method="post" modelAttribute="timesheetWeek">
        <form:hidden path="dateMonday"/>
        <table class="table table-hover">
            <tr>
                <td rowspan="2"><a
                        href="/timesheets/add/${projectId}?mode=prev&mondaySelect=${timesheetWeek.dateMonday}"
                        class="btn btn-info rounded-0 text-light m-1"/>
                    <
                </td>
                <th>${timesheetWeek.dateMonday}</th>
                <th>${timesheetWeek.dateMonday.plusDays(1)}</th>
                <th>${timesheetWeek.dateMonday.plusDays(2)}</th>
                <th>${timesheetWeek.dateMonday.plusDays(3)}</th>
                <th>${timesheetWeek.dateMonday.plusDays(4)}</th>
                <th>${timesheetWeek.dateMonday.plusDays(5)}</th>
                <th>${timesheetWeek.dateMonday.plusDays(6)}</th>
                <td rowspan="2"><a
                        href="/timesheets/add/${projectId}?mode=next&mondaySelect=${timesheetWeek.dateMonday}"
                        class="btn btn-info rounded-0 text-light m-1"/>
                    >
                </td>
            </tr>
            <tr>
                <td>
                    <label for="mondayMinutesId"></label>
                    <form:input type="number" path="mondayHours" id="mondayMinutesId" step="1" min="0" max="24"
                                class="form-control"/>
                    <form:errors path="mondayHours" cssClass="text-warning"/>
                </td>
                <td>
                    <label for="tuesdayMinutesId"></label>
                    <form:input type="number" path="tuesdayHours" id="tuesdayMinutesId" step="1" min="0" max="24"
                                class="form-control"/>
                    <form:errors path="tuesdayHours" cssClass="text-warning"/>
                </td>
                <td>
                    <label for="wednesdayMinutesId"></label>
                    <form:input type="number" path="wednesdayHours" id="wednesdayMinutesId" step="1" min="0" max="24"
                                class="form-control"/>
                    <form:errors path="wednesdayHours" cssClass="text-warning"/>
                </td>
                <td>
                    <label for="thursdayMinutesId"></label>
                    <form:input type="number" path="thursdayHours" id="thursdayMinutesId" step="1" min="0" max="24"
                                class="form-control"/>
                    <form:errors path="thursdayHours" cssClass="text-warning"/>
                </td>
                <td>
                    <label for="fridayMinutesId"></label>
                    <form:input type="number" path="fridayHours" id="fridayMinutesId" step="1" min="0" max="24"
                                class="form-control"/>
                    <form:errors path="fridayHours" cssClass="text-warning"/>
                </td>
                <td>
                    <label for="saturdayMinutesId"></label>
                    <form:input type="number" path="saturdayHours" id="saturdayMinutesId" step="1" min="0" max="24"
                                class="form-control"/>
                    <form:errors path="saturdayHours" cssClass="text-warning"/>
                </td>
                <td>
                    <label for="sundayMinutesId"></label>
                    <form:input type="number" path="sundayHours" id="sundayMinutesId" step="1" min="0" max="24"
                                class="form-control"/>
                    <form:errors path="sundayHours" cssClass="text-warning"/>
                </td>
            </tr>
        </table>


        <table class="table table-hover">
            <tr>
                <th style="width: 28%">Description of work done on ${timesheetWeek.dateMonday}</th>
                <td>
                    <form:textarea path="commentary.mondayCommentary" cols="135" rows="3" maxlength="650"
                                   cssStyle="resize: none" class="form-control"/>
                    <form:errors path="commentary.mondayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th style="width: 28%">Description of work done on ${timesheetWeek.dateMonday.plusDays(1)}</th>
                <td>
                    <form:textarea path="commentary.tuesdayCommentary" cols="135" rows="3" maxlength="650"
                                   cssStyle="resize: none" class="form-control"/>
                    <form:errors path="commentary.tuesdayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th style="width: 28%">Description of work done on ${timesheetWeek.dateMonday.plusDays(2)}</th>
                <td>
                    <form:textarea path="commentary.wednesdayCommentary" cols="135" rows="3" maxlength="650"
                                   cssStyle="resize: none" class="form-control"/>
                    <form:errors path="commentary.wednesdayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th style="width: 28%">Description of work done on ${timesheetWeek.dateMonday.plusDays(3)}</th>
                <td>
                    <form:textarea path="commentary.thursdayCommentary" cols="135" rows="3" maxlength="650"
                                   cssStyle="resize: none" class="form-control"/>
                    <form:errors path="commentary.thursdayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th style="width: 28%">Description of work done on ${timesheetWeek.dateMonday.plusDays(4)}</th>
                <td>
                    <form:textarea path="commentary.fridayCommentary" cols="135" rows="3" maxlength="650"
                                   cssStyle="resize: none" class="form-control"/>
                    <form:errors path="commentary.fridayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th style="width: 28%">Description of work done on ${timesheetWeek.dateMonday.plusDays(5)}</th>
                <td>
                    <form:textarea path="commentary.saturdayCommentary" cols="135" rows="3" maxlength="650"
                                   cssStyle="resize: none" class="form-control"/>
                    <form:errors path="commentary.saturdayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th style="width: 28%">Description of work done on ${timesheetWeek.dateMonday.plusDays(6)}</th>
                <td>
                    <form:textarea path="commentary.sundayCommentary" cols="135" rows="3" maxlength="650"
                                   cssStyle="resize: none" class="form-control"/>
                    <form:errors path="commentary.sundayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
        </table>

        <div style="text-align: center">
            <input type="submit" value="Save" class="btn-lg">
        </div>
    </form:form>
</div>
<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
