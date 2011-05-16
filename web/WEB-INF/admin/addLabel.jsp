<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="ru.ifmo.eshop.storage.Label" %>
<script type="text/javascript">
    function onSubmit(event) {
        var title=document.getElementById("title");
        var country=document.getElementById("country");
        error=false;
        if (title.value==null || title.length==0
            || title.value.length > <%= Label.TITLE_LENGTH %>) {
            error=true;
            title.style.border='1px solid #f00';
        } else {
            title.style.boeder='';
        }
        if (country.value!=null &&
            country.value.length > <%= Label.COUNTRY_LENGTH %>) {
            error=true;
            country.style.border='1px solid #f00';
        } else {
            country.style.boeder='';
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
if (act!=null && act.equals("edit")) {
    if (request.getParameter("id")!=null) {
        add=false;
    } else {
        error=true;
    }
}
if (error) {%>
    <h1><%= messages.getString("messages.error.id") %></h1>
<%} else {
    if(add){%>
<b><%= messages.getString("admin.forms.labels.add") %></b><br/><br/>
<form method="post" action="/admin/label" id="labelForm" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="add"/>
    <%= messages.getString("admin.forms.labels.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title"/>
    <small><%= messages.getString("admin.forms.labels.title.notice") %></small><br/>
    <%= messages.getString("admin.forms.labels.country") %>:<br/>
    <input type="text" name="country" id="country"><br/>
    <small><%= messages.getString("admin.forms.labels.country.notice") %></small><br/>
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<%} else {%>
<storage:record entity="Label" identity="<%= request.getParameter("id") %>"
message="<%= messages.getString("messages.label.lost")%>">
<b><%= messages.getString("admin.forms.labels.edit") %></b><br/><br/>
<form method="post" action="/admin/label" id="labelForm" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="save"/>
    <input type="hidden" name="id" value="<storage:label field="id"/>"/>
    <%= messages.getString("admin.forms.labels.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title" value="<storage:label field="title"/>"/>
    <small><%= messages.getString("admin.forms.labels.title.notice") %></small><br/>
    <%= messages.getString("admin.forms.labels.country") %>:
    <input type="text" name="country" id="country" value="<storage:label field="country"/>">
    <small><%= messages.getString("admin.forms.labels.country.notice") %></small><br/>
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
<br/><small><%= messages.getString("forms.mandatory") %></small>
</storage:record>
<% }
}%>