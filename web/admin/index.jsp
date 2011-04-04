<%@page contentType="text/jsp" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>

<!--
????? ????????? ????? ?? ??????? ??????? ???? ????????
-->
<template:insert template="/admin/adminTemplate.jsp">
  <template:put name='title' content='Eshop Admin' direct='true'/>
  <template:put name='header' content='header.jsp'/>
  <template:put name='menu' content='menu.html'/>
  <template:put name='content' content='content.jsp' />
</template:insert>