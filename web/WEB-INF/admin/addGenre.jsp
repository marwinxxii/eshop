<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.ResourceBundle" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
String act=request.getParameter("act");
boolean add=true;
//TODO check id
if (request.getParameter("act").equals("edit") && request.getParameter("id")!=null) {
    add=false;
}
if(add){
%>

<b><%= messages.getString("admin.forms.genres.add") %></b><br/><br/>
<form method="post" action="/admin/genres/submit">
    <%= messages.getString("admin.forms.genres.title") %>:
    <input type="text" name="title"/>
    <small><%= messages.getString("admin.forms.genres.title.notice") %></small><br/>
    <%= messages.getString("admin.forms.genres.desc") %>:<br/>
    <textarea name="description" rows="20" cols="80"></textarea><br/>
    <small><%= messages.getString("admin.forms.genres.desc.notice") %></small><br/>
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<%} else {%>
<b><%= messages.getString("admin.forms.genres.edit") %></b><br/><br/>
<form method="post" action="/admin/genres/save">
    <input type="hidden" name="id" value="<%= "1" %>"/>
    <%= messages.getString("admin.forms.genres.title") %>:
    <input type="text" name="title" value="<%= "Dark Metal" %>"/>
    <small><%= messages.getString("admin.forms.genres.title.notice") %></small><br/>
    <%= messages.getString("admin.forms.genres.desc") %>:<br/>
    <textarea name="description" rows="20" cols="80"><%= "Some description..." %>
    </textarea><br/>
    <small><%= messages.getString("admin.forms.genres.desc.notice") %></small><br/>
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
<% } %>