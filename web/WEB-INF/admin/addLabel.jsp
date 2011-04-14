<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="ru.ifmo.eshop.Eshop" %>
<%@ page import="ru.ifmo.eshop.storage.Label" %>
<%@ page import="ru.ifmo.eshop.storage.StorageManager" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
String act=request.getParameter("act");
boolean add=true;
boolean error=false;
int id=0;
if (act!=null && act.equals("edit") && request.getParameter("id")!=null) {
    if (request.getParameter("id")!=null) {
        add=false;
        try {
            id=Integer.valueOf(request.getParameter("id"));
            if (id<=0) error=true;
        } catch(NumberFormatException e) {
            error=true;
        }
    } else {
        error=true;
    }
    add=false;
}
if (error) {
    //TODO error message
    %>
    <h1>Wrong link</h1>
    <%
} else {
    if(add){
%>

<b><%= messages.getString("admin.forms.labels.add") %></b><br /><br/>
<form method="post" action="/admin/label">
    <!--<input type="hidden" name="act" value="add"/>-->
    <%= messages.getString("admin.forms.labels.title") %>:
    <input type="text" name="title" />
    <small><%= messages.getString("admin.forms.labels.title.notice") %></small><br />
    <%= messages.getString("admin.forms.labels.country") %>:
    <input type="text" name="country" />
    <small><%= messages.getString("admin.forms.labels.country.notice") %></small><br />
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<%} else {
    Label label=null;
    try {
        StorageManager sm = Eshop.getStorageManager();
        label=sm.getLabel(id);
        sm.close();
    } catch (ClassNotFoundException ex) {
        //TODO logging and exceptions
        response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
    } catch (SQLException ex) {
        response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
    }
    if (label==null) {
        //TODO not found message
        %>
        <h1>Wrong link</h1>
        <%
        return;
    }
%>
<b><%= messages.getString("admin.forms.labels.edit") %></b><br /><br/>
<form method="post" action="/admin/label">
    <input type="hidden" name="act" value="save"/>
    <input type="hidden" name="id" value="<%= label.getId() %>"/>
    <%= messages.getString("admin.forms.labels.title") %>:
    <input type="text" name="title" value="<%= label.getTitle() %>" />
    <small><%= messages.getString("admin.forms.labels.title.notice") %></small><br />
    <%= messages.getString("admin.forms.labels.country") %>:
    <input type="text" name="country" value="<%= label.getCountry() %>" />
    <small><%= messages.getString("admin.forms.labels.country.notice") %></small><br />
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
<% }
}%>