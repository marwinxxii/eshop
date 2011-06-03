<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="ru.ifmo.eshop.storage.Distributor" %>
<script type="text/javascript">
    function onSubmit(event) {
        var title=document.getElementById("title");
        var type=document.getElementById("type");
        var country=document.getElementById("country");
        var error=false;
        if (title.value==null || title.value.length==0
            || title.value.length > <%= Distributor.TITLE_LENGTH %>) {
            error=true;
            title.style.border='1px solid #f00';
        } else {
            title.style.border='';
        }
        if (country.value==null || country.value.length==0 ||
            country.value.length > <%= Distributor.COUNTRY_LENGTH %>) {
            error=true;
            country.style.border='1px solid #f00';
        } else {
            country.style.border='';
        }
        if (type.value==null || type.value.length==0 ||
            type.value.length > <%= Distributor.TYPE_LENGTH %>) {
            error=true;
            type.style.border='1px solid #f00';
        } else {
            type.style.border='';
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
<b><%= messages.getString("admin.forms.distributors.add") %></b><br /><br/>
<form method="post" action="/admin/distributor" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="add"/>
    <%= messages.getString("admin.forms.distributors.type") %><span class="red">*</span>:
    <input type="text" name="type" id="type" maxlength="<%= Distributor.TYPE_LENGTH %>" /><br/>
    <%= messages.getString("admin.forms.distributors.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title" maxlength="<%= Distributor.TITLE_LENGTH %>" />
    <small><%= messages.getString("admin.forms.distributors.title.notice") %></small><br />
    <%= messages.getString("admin.forms.distributors.country") %><span class="red">*</span>:
    <input type="text" name="country" id="country" maxlength="<%= Distributor.COUNTRY_LENGTH %>" /><br/>
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<%} else {%>
<storage:manager/>
<storage:distributor keyId="<%= id %>">
message="<%= messages.getString("messages.distributor.lost")%>">
<b><%= messages.getString("admin.forms.distributors.edit") %></b><br /><br/>
<form method="post" action="/admin/distributor" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="save"/>
    <input type="hidden" name="id" value="<storage:field name="id"/>"/>
    <%= messages.getString("admin.forms.distributors.type") %><span class="red">*</span>:
    <input type="text" name="type" id="type"
           maxlength="<%= Distributor.TYPE_LENGTH %>"
           value="<storage:field name="type"/>"/><br/>
    <%= messages.getString("admin.forms.distributors.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title"
           maxlength="<%= Distributor.TITLE_LENGTH %>"
           value="<storage:field name="title"/>"/>
    <small><%= messages.getString("admin.forms.distributors.title.notice") %></small><br />
    <%= messages.getString("admin.forms.distributors.country") %><span class="red">*</span>:
    <input type="text" name="country" id="country"
           maxlength="<%= Distributor.COUNTRY_LENGTH %>"
           value="<storage:field name="country"/>"/><br/>
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
</storage:distributor>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<% }
}%>