<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="java.util.ResourceBundle" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
%>
<ul id="menu">
    <li class="menuHeader"><%= messages.getString("admin.menu.title") %></li>
    <li><a href="/admin"><%= messages.getString("admin.menu.home") %></a></li>
    <li><a href="/admin/labels.jsp"><%= messages.getString("admin.menu.labels") %></a></li>
    <li><a href="/admin/genres.jsp"><%= messages.getString("admin.menu.genres") %></a></li>
    <li><a href="/admin/artists.jsp"><%= messages.getString("admin.menu.artists") %></a></li>
    <li><a href="/admin/tracks.jsp"><%= messages.getString("admin.menu.tracks") %></a></li>
    <li><a href="/admin/items.jsp"><%= messages.getString("admin.menu.items") %></a></li>
    <li><a href="/admin/distributors.jsp"><%= messages.getString("admin.menu.distributors") %></a></li>
    <li><a href="/admin/deliveries.jsp"><%= messages.getString("admin.menu.deliveries") %></a></li>
    <li>
        <br/>
        <a href="/admin/logout">
            <%= messages.getString("admin.menu.logout") %>
        </a>
    </li>
</ul>