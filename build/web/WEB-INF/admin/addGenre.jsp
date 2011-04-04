<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.ResourceBundle" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
%>

<b><%= messages.getString("admin.forms.genres.add") %></b><br/>
<form method="post" action="/admin/genres/submit">
    <%= messages.getString("admin.forms.genres.title") %>: <input type="text" name="title"/><br/>
    <%= messages.getString("admin.forms.genres.desc") %>:<br/>
    <textarea name="description" rows="20" cols="50"></textarea><br/>
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>