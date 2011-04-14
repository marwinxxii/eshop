<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="ru.ifmo.eshop.Eshop" %>
<%@ page import="ru.ifmo.eshop.storage.Genre" %>
<%@ page import="ru.ifmo.eshop.storage.StorageManager" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
List<Genre> genres=null;
try {
    StorageManager sm = Eshop.getStorageManager();
    genres=sm.getLastGenres();
    sm.close();
} catch (ClassNotFoundException ex) {
    //TODO logging and exceptions
    ex.printStackTrace(response.getWriter());
    response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
} catch (SQLException ex) {
    ex.printStackTrace(response.getWriter());
    response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
}
if (genres==null || genres.size()==0) {
    //TODO not found message
    %>
    <h1>No genres found</h1><br/>
    <b><a href="/admin/genres.jsp?act=add">Add new genre</a></b>
    <%
    return;
}
%>

<b><%= messages.getString("admin.forms.genres.view") %></b><br/><br/>
<table width="100%" cellpadding="0" cellspacing="0" style="text-align: center">
    <tr style="background-color: #ccc">
        <td width="5%">
            <b>ID</b>
        </td>
        <td width="35%">
            <b><%= messages.getString("admin.forms.genres.title") %></b>
        </td>
        <td width="40%">
            <b><%= messages.getString("admin.forms.genres.desc") %></b>
        </td>
        <td width="20%">
            <b>Actions</b>
        </td>
    </tr>
    <%
    for (Genre g:genres) {
        //TODO delete genre
    %>
    <tr style="background-color: #ccc">
        <td>
            <%= g.getId() %>
        </td>
        <td>
            <%= g.getTitle() %>
        </td>
        <td>
            <%= g.getDescription()+"..." %>
        </td>
        <td>
            <a href="/admin/genres.jsp?act=edit&id=<%= g.getId() %>">
                <%= messages.getString("forms.edit") %>
            </a>
            <a href="/admin/genres.jsp?act=del&id=<%= g.getId() %>">
                <%= messages.getString("forms.delete") %>
            </a>
        </td>
    </tr>
    <%
    }
    %>
</table><br/>
<b><a href="/admin/genres.jsp?act=add">Add new genre</a></b>