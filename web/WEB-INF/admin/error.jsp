<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.ResourceBundle" %>
<%
Cookie[] cookies=request.getCookies();
if (cookies==null || cookies.length==0) {
    response.sendRedirect("/admin");
} else {
    ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
    int errorCode=0;
    String returnPath="/admin";
    String message;
    for (Cookie c:cookies) {
        if (c.getName().equals("errorCode")) {
            errorCode=Integer.valueOf(c.getValue());
        }
        if (c.getName().equals("return")) {
            returnPath=c.getValue();
            if (!returnPath.startsWith("/admin"))
                returnPath="/admin";
        }
    }
    message=messages.getString("messages.error."+errorCode);
%>
<h1><%= message %></h1><br/>
<a href="<%= returnPath %>"><%= messages.getString("messages.return") %></a>
<%
}
%>