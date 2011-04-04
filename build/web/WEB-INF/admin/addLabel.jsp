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

<b><%= messages.getString("admin.forms.labels.add") %></b><br /><br/>
<form method="post" action="/admin/labels/submit">
    <%= messages.getString("admin.forms.labels.title") %>:
    <input type="text" name="title" />
    <small><%= messages.getString("admin.forms.labels.title.notice") %></small><br />
    <%= messages.getString("admin.forms.labels.country") %>:
    <input type="text" name="country" />
    <small><%= messages.getString("admin.forms.labels.country.notice") %></small><br />
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<% }else{ %>
<b><%= messages.getString("admin.forms.labels.edit") %></b><br /><br/>
<form method="post" action="/admin/labels/save">
    <input type="hidden" name="id" value="<%= "1" %>"/>
    <%= messages.getString("admin.forms.labels.title") %>:
    <input type="text" name="title" value="<%= "Profound Lore" %>" />
    <small><%= messages.getString("admin.forms.labels.title.notice") %></small><br />
    <%= messages.getString("admin.forms.labels.country") %>:
    <input type="text" name="country" value="<%= "United States" %>" />
    <small><%= messages.getString("admin.forms.labels.country.notice") %></small><br />
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
<% } %>