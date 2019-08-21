<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>LegalCRM start</title>
    <%@ include file="/fragments/head.jspf" %>
</head>
<body>
<%@ include file="/fragments/header.jspf" %>

<header class="w3-container w3-red w3-center" style="padding:128px 16px">
    <h1 class="w3-margin w3-jumbo">Legal CRM - Welcome</h1>
    <h3>Full access requires user authentication </h3> <br>
    <a href="/login" class="btn btn-warning btn-lg rounded-0 text-light m-1">LOGIN</a>
</header>

<div class="w3-row-padding w3-padding-64 w3-container">
    <div class="w3-content">
        <div class="w3-twothird">
            <h1>Why Should Lawyers Manage Their Client Relationships?</h1>
            <h5 class="w3-padding-32" style="text-align: justify">Busy lawyers deal with a lot of information. The
                paperless office is, in many ways, a pipe dream for legal professionals. Data organization and data
                collection is an important element of relationship management and business development. If lawyers
                better manage their client relationships and the associated information, they’ll benefit from happier
                customers, repeat business, and referrals.
            </h5>

            <p class="w3-text-grey" style="text-align: justify">The right client relationship management techniques will
                also give busy law firms something that they all can use more time in the day. Manually managing all
                your relationships is tedious and it can leave room for errors and omissions. That’s why lawyers should
                consider leveraging simple CRM software in their practice. This can work well for lawyers who work
                alone as well as for law firms with several lawyers, paralegals, clerks, and administrative staff
                members.</p>
        </div>

        <div class="w3-third w3-center">
            <i class="fa fa-anchor w3-padding-64 w3-text-red"></i>
        </div>
    </div>
</div>

<div class="w3-row-padding w3-light-grey w3-padding-64 w3-container">
    <div class="w3-content">
        <div class="w3-third w3-center">
            <i class="fa fa-coffee w3-padding-64 w3-text-red w3-margin-right"></i>
        </div>

        <div class="w3-twothird">
            <h1>What is CRM software?</h1>
            <h5 class="w3-padding-32" style="text-align: justify">CRM is customer relationship management. CRM software
                is a customer relationship management tool that helps you effectively manage your business
                relationships. CRM software is used in many businesses and can benefit anyone who has even one customer,
                let alone a large portfolio of existing and past clients.
            </h5>

            <p class="w3-text-grey" style="text-align: justify">Not only does the right CRM software help you with the
                logistics of managing your client relationships and your caseload but it also helps you manage leads as
                well as let you extract data to help you analyze relationships, your pipeline, your success rate with
                leads and clients, and so on. The right CRM software for lawyers will let you collaborate with other
                people in your law firm, as well. This is a time saving feature that can help you delegate to team
                members and communicate important information about cases, increasing cross-functional team efficacy.
                Having key information at your fingertips will help you better serve clients and provides a tool for
                gathering internal business intelligence. That business intelligence can help you plan, improve, market,
                and so on.
            </p>
        </div>
    </div>
</div>

<%@ include file="/fragments/footer.jspf" %>
</body>
</html>



