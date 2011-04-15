<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>

<template:insert template="/WEB-INF/admin/adminTemplate.jsp">
  <template:put name="header" content="/WEB-INF/admin/header.jsp"/>
  <template:put name="menu" content="/WEB-INF/admin/menu.jsp"/>
  <template:put name="content" content="/WEB-INF/admin/error.jsp" />
</template:insert>