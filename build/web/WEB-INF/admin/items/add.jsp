<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="ru.ifmo.eshop.storage.Item" %>
<script type="text/javascript">
    function onSubmit(event) {
        var title=document.getElementById("title");
        var labelId=document.getElementById("labelId");
        var format=document.getElementById("format");
        var mediaType=document.getElementById("mediaType");
        var cover=document.getElementById("cover");
        var releaseDate=document.getElementById("releaseDate");
        var error=false;
        if (title.value==null || title.length==0
            || title.value.length > <%= Item.TITLE_LENGTH %>) {
            error=true;
            title.style.border='1px solid #f00';
        } else {
            title.style.border='';
        }
        var i=parseInt(labelId.value);
        if (labelId.value!="" && (isNaN(i) || i<=0)) {
            error=true;
            labelId.style.border='1px solid #f00';
        } else {
            labelId.style.border='';
        }
        if (format.value==null || format.value.length==0
            || format.value.length><%= Item.FORMAT_LENGTH %>) {
            error=true;
            format.style.border='1px solid #f00';
        } else {
            format.style.border='';
        }
        if (mediaType.value==null || mediaType.value.length==0
            || mediaType.value.length > <%=Item.MEDIATYPE_LENGTH %>) {
            error=true;
            mediaType.style.border='1px solid #f00';
        } else {
            mediaType.style.border='';
        }
        if (cover.value!=null && cover.value.length!=0
            && cover.value.length > <%=Item.COVER_LENGTH %>) {
            error=true;
            cover.style.border='1px solid #f00';
        } else {
            cover.style.border='';
        }
        i=Date.parse(releaseDate.value);
        if (releaseDate.value=="" || isNaN(i)) {
            releaseDate.style.border='1px solid #f00';
            error=true;
        } else {
            releaseDate.style.border='';
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
<b><%= messages.getString("admin.forms.items.add") %></b><br /><br/>
<form method="post" action="/admin/item" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="add"/>
    <%= messages.getString("admin.forms.items.mediaType") %><span class="red">*</span>:
    <input type="text" name="mediaType" id="mediaType" maxlength="<%= Item.MEDIATYPE_LENGTH %>" /><br/>
    <%= messages.getString("admin.forms.items.format") %><span class="red">*</span>:
    <input type="text" name="format" id="format" /><br/>
    <%= messages.getString("admin.forms.items.labelId") %>:
    <input type="text" name="labelId" id="labelId" maxlength="6" />
    <a href="/admin/labels?act=add"><small><%= messages.getString("admin.forms.labels.add") %></small></a><br/>
    <%= messages.getString("admin.forms.items.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title" maxlength="<%= Item.TITLE_LENGTH %>" />
    <small><%= messages.getString("admin.forms.items.title.notice") %></small><br />
    <%= messages.getString("admin.forms.items.cover") %>:
    <input type="text" name="cover" id="cover" maxlength="<%= Item.COVER_LENGTH %>" />
    <small><%= messages.getString("admin.forms.items.cover.notice") %></small><br />
    <%= messages.getString("admin.forms.items.releaseDate") %><span class="red">*</span>:
    <input type="text" name="releaseDate" id="releaseDate" maxlength="10" />
    <small><%= messages.getString("admin.forms.items.releaseDate.notice") %></small><br />
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<%} else {%>
<storage:item keyId="<%= id %>"
message="<%= messages.getString("messages.item.lost")%>">
<b><%= messages.getString("admin.forms.items.edit") %></b><br /><br/>
<form method="post" action="/admin/item" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="save"/>
    <input type="hidden" name="id" value="<storage:field name="id"/>"/>
    <%= messages.getString("admin.forms.items.mediaType") %><span class="red">*</span>:
    <input type="text" name="mediaType" id="mediaType"
           maxlength="<%= Item.MEDIATYPE_LENGTH %>"
           value="<storage:field name="mediaType"/>" /><br/>
    <%= messages.getString("admin.forms.items.format") %><span class="red">*</span>:
    <input type="text" name="format" id="format"
           value="<storage:field name="format"/>" /><br/>
    <%= messages.getString("admin.forms.items.labelId") %>:
    <input type="text" name="labelId" id="labelId" maxlength="6"
           value="<storage:field name="label.id"/>" />
    <small><%= messages.getString("admin.forms.labels.add") %></small><br/>
    <%= messages.getString("admin.forms.items.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title"
           maxlength="<%= Item.TITLE_LENGTH %>"
           value="<storage:field name="title"/>" />
    <small><%= messages.getString("admin.forms.items.title.notice") %></small><br />
    <%= messages.getString("admin.forms.items.cover") %>:
    <input type="text" name="cover" id="cover"
           maxlength="<%= Item.COVER_LENGTH %>"
           value="<storage:field name="cover"/>" /><br/>
    <%= messages.getString("admin.forms.items.releaseDate") %><span class="red">*</span>:
    <input type="text" name="releaseDate" id="releaseDate"
           maxlength="10"
           value="<storage:field name="releaseDate"/>" />
    <small><%= messages.getString("admin.forms.items.releaseDate.notice") %></small><br />
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
</storage:item>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<% }
}%>