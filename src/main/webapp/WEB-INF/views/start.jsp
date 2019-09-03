<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">

<head>
    <title>LegalCRM</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%@ include file="/fragments/headStart.jspf" %>
</head>

<body>

<div class="slider-item overlay backgroundImage1" data-stellar-background-ratio="0.5">
    <div class="container">
        <div class="row slider-text align-items-center justify-content-center">
            <div class="col-lg-9 text-center col-sm-12 element-animate">
                <h1> Legal CRM</h1>
                <h1 style="font-size: 2em">Making business simpler</h1>

                <c:choose>
                    <c:when test="${not empty loggedInUser}">
                        <a href="/home" class="btn btn-success btn-lg rounded-0 text-light m-1"><h4>Go back to crm</h4></a>
                    </c:when>
                    <c:otherwise>
                        <a href="/login" class="btn btn-warning btn-lg rounded-0 text-light m-1"><h4>LOGIN</h4></a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="w3-content">
        <div class="w3-twothird">
            <h2>Why Should Lawyers Manage Their Client Relationships?</h2>
            <h4 class="text-justify">Busy lawyers deal with a lot of information. The
                paperless office is, in many ways, a pipe dream for legal professionals. Data organization and data
                collection is an important element of relationship management and business development. If lawyers
                better manage their client relationships and the associated information, they will benefit from happier
                customers, repeat business, and referrals.
            </h4>
            <h5 class="text-justify">The right client relationship management techniques will
                also give busy law firms something that they all can use more time in the day. Manually managing all
                your relationships is tedious and it can leave room for errors and omissions. That is why lawyers should
                consider leveraging simple CRM software in their practice. This can work well for lawyers who work
                alone as well as for law firms with several lawyers, paralegals, clerks, and administrative staff
                members.</h5>
        </div>
        <div class="w3-third w3-center">
            <i class="fa fa-anchor w3-padding-64 w3-text-red"></i>
        </div>
    </div>
</div>
<div class="container">
    <div class="w3-content">
        <div class="w3-third w3-center">
            <i class="fa fa-coffee w3-padding-64 w3-text-red w3-margin-right"></i>
        </div>
        <div class="w3-twothird">
            <h2>What is CRM software?</h2>
            <h4 class="text-justify">CRM is customer relationship management. CRM software
                is a customer relationship management tool that helps you effectively manage your business
                relationships. CRM software is used in many businesses and can benefit anyone who has even one customer,
                let alone a large portfolio of existing and past clients.
            </h4>
            <h5 class="text-justify">Not only does the right CRM software help you with the
                logistics of managing your client relationships and your caseload but it also helps you manage leads as
                well as let you extract data to help you analyze relationships, your pipeline, your success rate with
                leads and clients, and so on. The right CRM software for lawyers will let you collaborate with other
                people in your law firm, as well. This is a time saving feature that can help you delegate to team
                members and communicate important information about cases, increasing cross-functional team efficacy.
                Having key information at your fingertips will help you better serve clients and provides a tool for
                gathering internal business intelligence. That business intelligence can help you plan and improve.
            </h5>
        </div>
    </div>
</div>

<%@ include file="/fragments/footer.jspf" %>
<%@ include file="/fragments/jsCodeStart.jspf" %>


</body>

</html>