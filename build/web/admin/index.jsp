<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<bundle:get/>
<template:insert template="/WEB-INF/admin/template.jsp">
  <template:put name="header" content="/WEB-INF/admin/header.jsp"/>
  <template:put name="menu" content="/WEB-INF/admin/menu.jsp"/>
  <template:put name="content" content="/WEB-INF/admin/content.jsp" />
</template:insert>