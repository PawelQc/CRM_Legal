<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update timesheet</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>

<%@ include file="/fragments/header.jspf" %>

<div class="container">

    <h1 class="text-center">Update timesheet</h1>

    <form:form method="post" modelAttribute="workWeek" action="/timesheets/update">
        <form:hidden path="dateMonday"/>
        <form:hidden path="id"/>

        <table class="table table-hover">
            <tr>
                <th>${workWeek.dateMonday}</th>
                <th>${workWeek.dateMonday.plusDays(1)}</th>
                <th>${workWeek.dateMonday.plusDays(2)}</th>
                <th>${workWeek.dateMonday.plusDays(3)}</th>
                <th>${workWeek.dateMonday.plusDays(4)}</th>
                <th>${workWeek.dateMonday.plusDays(5)}</th>
                <th>${workWeek.dateMonday.plusDays(6)}</th>
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
                <th class="ts-form-desc">Description of work done on ${workWeek.dateMonday}</th>
                <td>
                    <form:textarea path="commentary.mondayCommentary" cols="135" rows="3" maxlength="650"
                                   class="form-control txt-area-no-resize"/>
                    <form:errors path="commentary.mondayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th class="ts-form-desc">Description of work done on ${workWeek.dateMonday.plusDays(1)}</th>
                <td>
                    <form:textarea path="commentary.tuesdayCommentary" cols="135" rows="3" maxlength="650"
                                   class="form-control txt-area-no-resize"/>
                    <form:errors path="commentary.tuesdayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th class="ts-form-desc">Description of work done on ${workWeek.dateMonday.plusDays(2)}</th>
                <td>
                    <form:textarea path="commentary.wednesdayCommentary" cols="135" rows="3" maxlength="650"
                                   class="form-control txt-area-no-resize"/>
                    <form:errors path="commentary.wednesdayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th class="ts-form-desc">Description of work done on ${workWeek.dateMonday.plusDays(3)}</th>
                <td>
                    <form:textarea path="commentary.thursdayCommentary" cols="135" rows="3" maxlength="650"
                                   class="form-control txt-area-no-resize"/>
                    <form:errors path="commentary.thursdayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th class="ts-form-desc">Description of work done on ${workWeek.dateMonday.plusDays(4)}</th>
                <td>
                    <form:textarea path="commentary.fridayCommentary" cols="135" rows="3" maxlength="650"
                                   class="form-control txt-area-no-resize"/>
                    <form:errors path="commentary.fridayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th class="ts-form-desc">Description of work done on ${workWeek.dateMonday.plusDays(5)}</th>
                <td>
                    <form:textarea path="commentary.saturdayCommentary" cols="135" rows="3" maxlength="650"
                                   class="form-control txt-area-no-resize"/>
                    <form:errors path="commentary.saturdayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
            <tr>
                <th class="ts-form-desc">Description of work done on ${workWeek.dateMonday.plusDays(6)}</th>
                <td>
                    <form:textarea path="commentary.sundayCommentary" cols="135" rows="3" maxlength="650"
                                   class="form-control txt-area-no-resize"/>
                    <form:errors path="commentary.sundayCommentary" element="div" cssClass="text-warning"/>
                </td>
            </tr>
        </table>

        <div class="text-center">
            <input type="submit" value="Update" class="btn-lg btn-success">
        </div>
    </form:form>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>
