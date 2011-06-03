<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="ru.ifmo.eshop.storage.Track" %>
<script type="text/javascript">
    function onSubmit(event) {
        var title=document.getElementById("title");
        var artistId=document.getElementById("labelId");
        var itemId=document.getElementById("itemId");
        var trackNumber=document.getElementById("trackNumber");
        var composer=document.getElementById("composer");
        var duration=document.getElementById("duration");
        var error=false;
        if (title.value==null || title.length==0
            || title.value.length > <%= Track.TITLE_LENGTH %>) {
            error=true;
            title.style.border='1px solid #f00';
        } else {
            title.style.border='';
        }
        var i=parseInt(artistId.value);
        if (isNaN(i) || i<=0) {
            error=true;
            artistId.style.border='1px solid #f00';
        } else {
            artistId.style.border='';
        }
        i=parseInt(itemId.value);
        if (isNaN(i) || i<=0) {
            error=true;
            itemId.style.border='1px solid #f00';
        } else {
            itemId.style.border='';
        }
        i=parseInt(trackNumber.value);
        if (isNaN(i) || i<=0 || i>99) {
            error=true;
            trackNumber.style.border='1px solid #f00';
        } else {
            trackNumber.style.border='';
        }
        if (composer.value!=null &&
            composer.value.length > <%=Track.COMPOSER_LENGTH %>) {
            error=true;
            composer.style.border='1px solid #f00';
        } else {
            composer.style.border='';
        }
        if (duration.value!=null &&
            duration.value.length > <%=Track.DURATION_LENGTH %>) {
            error=true;
            duration.style.border='1px solid #f00';
        } else {
            duration.style.border='';
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
<b><%= messages.getString("admin.forms.tracks.add") %></b><br /><br/>
<form method="post" action="/admin/track" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="add"/>
    <%= messages.getString("admin.forms.tracks.artistId") %><span class="red">*</span>:
    <input type="text" name="artistId" id="artistId" maxlength="6" />
    <a href="/admin/artists.jsp?act=add">
        <small><%= messages.getString("admin.forms.artists.add") %></small>
    </a><br/>
    <%= messages.getString("admin.forms.tracks.itemId") %><span class="red">*</span>:
    <input type="text" name="itemId" id="itemId" maxlength="6" />
    <a href="/admin/items.jsp?act=add">
        <small><%= messages.getString("admin.forms.items.add") %></small>
    </a><br/>
    <%= messages.getString("admin.forms.tracks.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title" maxlength="<%= Track.TITLE_LENGTH %>" />
    <small><%= messages.getString("admin.forms.tracks.title.notice") %></small><br />
    <%= messages.getString("admin.forms.tracks.trackNumber") %><span class="red">*</span>:
    <input type="text" name="trackNumber" id="trackNumber" maxlength="2" /><br/>
    <%= messages.getString("admin.forms.tracks.composer") %>:
    <input type="text" name="composer" id="composer" maxlength="<%= Track.COMPOSER_LENGTH %>" />
    <small><%= messages.getString("admin.forms.tracks.composer.notice") %></small><br />
    <input type="text" name="duration" id="duration" maxlength="<%= Track.DURATION_LENGTH %>" />
    <small>0:00:00 <%= messages.getString("admin.forms.tracks.duration.notice") %></small><br />
    <%= messages.getString("admin.forms.tracks.isVideo") %>:
    <input type="checkbox" name="isVideo"/><br/>
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<%} else {%>
<storage:manager/>
<storage:track keyId="<%= id %>"
message="<%= messages.getString("messages.track.lost")%>">
<b><%= messages.getString("admin.forms.tracks.edit") %></b><br /><br/>
<form method="post" action="/admin/track" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="save"/>
    <input type="hidden" name="id" value="<storage:field name="id"/>"/>
    <%= messages.getString("admin.forms.tracks.artistId") %><span class="red">*</span>:
    <input type="text" name="artistId" id="artistId" maxlength="6"
           value="<storage:field name="artist.id"/>"/>
    <a href="/admin/artists.jsp?act=add">
        <small><%= messages.getString("admin.forms.artists.add") %></small>
    </a><br/>
    <%= messages.getString("admin.forms.tracks.itemId") %><span class="red">*</span>:
    <input type="text" name="itemId" id="itemId" maxlength="6"
           value="<storage:field name="item.id"/>" />
    <a href="/admin/items.jsp?act=add">
        <small><%= messages.getString("admin.forms.items.add") %></small>
    </a><br/>
    <%= messages.getString("admin.forms.tracks.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title" maxlength="<%= Track.TITLE_LENGTH %>"
           value="<storage:field name="title"/>"/>
    <small><%= messages.getString("admin.forms.tracks.title.notice") %></small><br />
    <%= messages.getString("admin.forms.tracks.trackNumber") %><span class="red">*</span>:
    <input type="text" name="trackNumber" id="trackNumber" maxlength="2"
           value="<storage:field name="trackNumber"/>"/><br/>
    <%= messages.getString("admin.forms.tracks.composer") %>:
    <input type="text" name="composer" id="composer" maxlength="<%= Track.COMPOSER_LENGTH %>"
           value="<storage:field name="composer"/>"/>
    <small><%= messages.getString("admin.forms.tracks.composer.notice") %></small><br />
    <input type="text" name="duration" id="duration" maxlength="<%= Track.DURATION_LENGTH %>"
           value="<storage:field name="duration"/>"/>
    <small>0:00:00 <%= messages.getString("admin.forms.tracks.duration.notice") %></small><br />
    <%= messages.getString("admin.forms.tracks.isVideo") %>:
    <input type="checkbox" name="isVideo" checked="<storage:field name="isVideo"/>"/><br/>
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
</storage:track>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<% }
}%>