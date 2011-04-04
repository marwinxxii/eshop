<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.ResourceBundle" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
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
    <tr>
        <td>
            1
        </td>
        <td>
            Dark Metal
        </td>
        <td>
            Some description...
        </td>
        <td>
            <a href="?act=edit&id=1"><%= messages.getString("forms.edit") %></a>
            <a href="#"><%= messages.getString("forms.delete") %></a>
        </td>
    </tr>
    <tr>
        <td>
            2
        </td>
        <td>
            Heavy Metal
        </td>
        <td>
            Another one metal genre
        </td>
        <td>
            <a href="#"><%= messages.getString("forms.edit") %></a>
            <a href="#"><%= messages.getString("forms.delete") %></a>
        </td>
    </tr>
</table><br/>
<b><a href="/admin/genres.jsp?act=add">Add new genre</a></b>