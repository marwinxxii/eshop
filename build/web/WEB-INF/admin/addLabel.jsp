<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.ResourceBundle" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
%>

<b><%= messages.getString("admin.forms.labels.add") %></b><br />
<form method="post" action="/admin/labels/submit">
    <%= messages.getString("admin.forms.labels.title") %>: <input type="text" name="title" /><br />
    <%= messages.getString("admin.forms.labels.country") %>: <input type="text" name="country" /><br />
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>