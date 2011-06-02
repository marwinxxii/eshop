<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.MissingResourceException" %>
<%@ page import="java.util.Locale" %>
<%
Locale locale = request.getLocale();
String lang = "";
if (request.getParameter("locale") != null) {
    lang = request.getParameter("locale").toLowerCase();
    if (lang.equals("ru")) {
        locale = new Locale("ru", "RU");
        Cookie c = new Cookie("locale", "ru");
        c.setPath("/");
        c.setMaxAge(315360000);
        response.addCookie(c);
    } else {
        lang = "en";
        locale = new Locale("en", "US");
        Cookie c = new Cookie("locale", "en");
        c.setPath("/");
        c.setMaxAge(315360000);
        response.addCookie(c);
    }
} else {
    lang = "en";
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
        if (locale == null) {
            locale = new Locale("en", "US");
            Cookie c = new Cookie("locale", "en");
            c.setPath("/");
            c.setMaxAge(315360000);
            response.addCookie(c);
        }
    } else {
        boolean cookieSet = false;
        for (Cookie c : cookies) {
            if (c.getName().equals("locale")) {
                if (c.getValue() != null) {
                    lang = c.getValue().toLowerCase();
                    if (lang.equals("ru")) {
                        locale = new Locale("ru", "RU");
                    } else {
                        lang = "en";
                        locale = new Locale("en", "US");
                    }
                    cookieSet = true;
                    break;
                }
            }
        }
        if (!cookieSet) {
            Cookie c = new Cookie("locale", "en");
            c.setPath("/");
            c.setMaxAge(315360000);
            response.addCookie(c);
        }
    }
}
ResourceBundle messages;

try {
    messages = ResourceBundle.getBundle("ru.ifmo.eshop.i18n.MessagesBundle", locale);
} catch (MissingResourceException e) {
    locale = new Locale("en", "US");
    messages = ResourceBundle.getBundle("ru.ifmo.eshop.i18n.MessagesBundle", locale);
}
pageContext.setAttribute("resourceBundle", messages,PageContext.REQUEST_SCOPE);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="/static/main.css" />
        <link type="text/css" rel="stylesheet" href="/admin/static/admin.css" />
        <title><%= messages.getString("admin.title") %></title>
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