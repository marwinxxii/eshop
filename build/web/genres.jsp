<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>

<template:insert template="/WEB-INF/user/template.jsp">
    <% if (request.getParameter("id")!=null) {%>
    <template:put name="content" content="/WEB-INF/user/genres/genre.jsp"/>
    <% } else {%>
    <template:put name="content" content="/WEB-INF/user/genres/rating.jsp" />
    <% } %>
</template:insert>
