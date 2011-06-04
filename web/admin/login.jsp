<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.MissingResourceException" %>
<%@ page import="java.util.Locale" %>
<%
Cookie[] cookies=request.getCookies();
String ret="/admin/index.jsp";
if (cookies!=null) {
    for (Cookie c:cookies) {
        if (c.getName().equals("return")) {
            ret=c.getValue();
            break;
        }
    }
}
ResourceBundle messages;
//Locale locale=request.getLocale();
Locale locale = new Locale("en", "US");
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
        <title>Eshop Admin Login</title>
    </head>
    <body>
        <center>
            <h2><bundle:message key="admin.forms.signin.greeting"/></h2>
            <form method="post" action="/admin/login">
                <input type="hidden" name="return" value="<%= ret %>"/>
                <bundle:message key="admin.forms.signin.login"/>:
                <input type="textbox" name="login"/><br/>
                <bundle:message key="admin.forms.signin.password"/>:
                <input type="password" name="password"/><br/>
                <input type="submit" value="Sign in"/>
            </form>
        </center>
    </body>
</html>
