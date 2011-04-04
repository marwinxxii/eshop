<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>

<template:insert template="/WEB-INF/admin/adminTemplate.jsp">
    <template:put name="header" content="/WEB-INF/admin/header.html"/>
    <template:put name="menu" content="/WEB-INF/admin/menu.jsp"/>
    <%
    if (request.getParameter("act")!=null
            && (request.getParameter("act").equals("add")
            || request.getParameter("act").equals("edit"))) {
        %>
    <template:put name="content" content="/WEB-INF/admin/addLabel.jsp" />
    <% } else { %>
    <template:put name="content" content="/WEB-INF/admin/labels.jsp" />
    <% }%>
</template:insert>