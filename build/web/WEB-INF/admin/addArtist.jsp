<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.ResourceBundle" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
%>

<b><%= messages.getString("admin.forms.artists.add") %></b><br />
<form method="post" action="/admin/labels/submit">
    <%= messages.getString("admin.forms.artists.title") %>: <input type="text" name="title" /><br />
    <%= messages.getString("admin.forms.artists.genreId") %>: <input type="text" name="genre_id" /><br />
    <%= messages.getString("admin.forms.artists.country") %>: <input type="text" name="country" /><br />
    <%= messages.getString("admin.forms.artists.beginYear") %>: <input type="text" name="begin_year" /><br />
    <%= messages.getString("admin.forms.artists.endYear") %>: <input type="text" name="end_year" /><br />
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>