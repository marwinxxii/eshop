<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="ru.ifmo.eshop.Eshop" %>
<%@ page import="ru.ifmo.eshop.storage.Genre" %>
<%@ page import="ru.ifmo.eshop.storage.StorageManager" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
String act=request.getParameter("act");
boolean add=true;
int id=0;
boolean error=false;
if (act!=null && act.equals("edit")) {
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
}
if (error) {
    //TODO error message
    %>
    <h1>Wrong link</h1>
    <%
} else {
    if(add){
%>

<b><%= messages.getString("admin.forms.genres.add") %></b><br/><br/>
<form method="post" action="/admin/genre">
    <!--<input type="hidden" name="act" value="add"/>-->
    <%= messages.getString("admin.forms.genres.title") %>:
    <input type="text" name="title"/>
    <small><%= messages.getString("admin.forms.genres.title.notice") %></small><br/>
    <%= messages.getString("admin.forms.genres.desc") %>:<br/>
    <textarea name="description" rows="10" cols="80"></textarea><br/>
    <small><%= messages.getString("admin.forms.genres.desc.notice") %></small><br/>
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<%} else {
    Genre g=null;
    try {
        StorageManager sm = Eshop.getStorageManager();
        g=sm.getGenre(id);
        sm.close();
    } catch (ClassNotFoundException ex) {
        //TODO logging and exceptions
        response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
    } catch (SQLException ex) {
        response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
    }
    if (g==null) {
        //TODO not found message
        %>
        <h1>Wrong link</h1>
        <%
        return;
    }
%>
<b><%= messages.getString("admin.forms.genres.edit") %></b><br/><br/>
<form method="post" action="/admin/genre">
    <input type="hidden" name="act" value="save"/>
    <input type="hidden" name="id" value="<%= g.getId() %>"/>
    <%= messages.getString("admin.forms.genres.title") %>:
    <input type="text" name="title" value="<%= g.getTitle() %>"/>
    <small><%= messages.getString("admin.forms.genres.title.notice") %></small><br/>
    <%= messages.getString("admin.forms.genres.desc") %>:<br/>
    <textarea name="description" rows="10" cols="80"><%= g.getDescription() %></textarea>
    <br/>
    <small><%= messages.getString("admin.forms.genres.desc.notice") %></small><br/>
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
<% }
}%>