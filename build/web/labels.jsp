<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<bundle:get/>
<template:insert template="/WEB-INF/user/template.jsp">
    <% if (request.getParameter("id")!=null) {%>
    <template:put name="content" content="/WEB-INF/user/labels/label.jsp"/>
    <% } else {%>
    <template:put name="content" content="/WEB-INF/user/labels/rating.jsp" />
    <% } %>
</template:insert>
