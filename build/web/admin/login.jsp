<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<bundle:get/>
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
