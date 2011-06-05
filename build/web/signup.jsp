<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<bundle:get/>
<template:insert template="/WEB-INF/user/template.jsp">
    <template:put name="content" content="/WEB-INF/user/signup.jsp" />
</template:insert>
