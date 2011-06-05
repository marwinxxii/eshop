<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="/admin/static/main.css" />
        <title><bundle:message key="admin.title"/></title>
        <template:get name="scripts"/>
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
            <br/>
            <center>
                <a href="?locale=en">English</a> |
                <a href="?locale=ru">Русский</a>
            </center>
    </body>
</html>