<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>

<template:insert template="/WEB-INF/user/template.jsp">
  <template:put name="content" content="/WEB-INF/user/items/index.jsp" />
</template:insert>