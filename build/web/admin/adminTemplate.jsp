<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<!--
Здесь определяем расположение элементов на странице
-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><template:get name="title"/></title>
    </head>
    <body>
        <div align="center">
            <template:get name="header"/>
        </div>
        <div id="body">
            <div id="left">
                <template:get name="menu"/>
            </div>
            <div id="right">
                <template:get name="content"/>
            </div>
        </div>
    </body>
</html>