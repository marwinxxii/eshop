<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="ru.ifmo.eshop.storage.Genre" %>
<script type="text/javascript">
    function onSubmit(event) {
        var title=document.getElementById("title");
        var desc=document.getElementById("desc");
        error=false;
        if (title.value==null || title.length==0
            || title.value.length > <%= Genre.TITLE_LENGTH %>) {
            error=true;
            title.style.border='1px solid #f00';
        } else {
            title.style.border='';
        }
        if (desc.value==null || desc.value.length==0
            || desc.value.length > <%= Genre.DESCRIPTION_LENGTH %>) {
            error=true;
            desc.style.border='1px solid #f00';
        } else {
            desc.style.border='';
        }
        if (error) {
            event.preventDefault();
        }
    }
</script>
<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
String act=request.getParameter("act");
boolean add=true;
boolean error=false;
int id=0;
if (act!=null && act.equals("edit")) {
    if (request.getParameter("id")!=null) {
        add=false;
        try {
            id=Integer.parseInt(request.getParameter("id"));
        } catch(NumberFormatException e) {
            error=true;
        }
    } else {
        error=true;
    }
}
if (error) {%>
    <h1><%= messages.getString("messages.error.id") %></h1>
<%} else {
    if(add){%>
<b><%= messages.getString("admin.forms.genres.add") %></b><br/><br/>
<form method="post" action="/admin/genre" id="genreForm" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="add"/>
    <%= messages.getString("admin.forms.genres.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title"/>
    <small><%= messages.getString("admin.forms.genres.title.notice") %></small><br/>
    <%= messages.getString("admin.forms.genres.desc") %><span class="red">*</span>:<br/>
    <textarea name="description" rows="10" cols="80" id="desc"></textarea><br/>
    <small><%= messages.getString("admin.forms.genres.desc.notice") %></small><br/>
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<%} else {%>
<storage:manager/>
<storage:genre keyId="<%= id %>"
message="<%= messages.getString("messages.genre.lost")%>">
<b><%= messages.getString("admin.forms.genres.edit") %></b><br/><br/>
<form method="post" action="/admin/genre" id="genreForm" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="save"/>
    <input type="hidden" name="id" value="<storage:field name="id"/>"/>
    <%= messages.getString("admin.forms.genres.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title" value="<storage:field name="title"/>"/>
    <small><%= messages.getString("admin.forms.genres.title.notice") %></small><br/>
    <%= messages.getString("admin.forms.genres.desc") %><span class="red">*</span>:<br/>
    <textarea name="description" id="desc" rows="10" cols="80"><storage:field name="description"/></textarea>
    <br/>
    <small><%= messages.getString("admin.forms.genres.desc.notice") %></small><br/>
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
<br/><small><%= messages.getString("forms.mandatory") %></small>
</storage:genre>
<% }
}%>