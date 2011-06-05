<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="ru.ifmo.eshop.storage.Artist" %>
<script type="text/javascript">
    function onSubmit(event) {
        var title=document.getElementById("title");
        var genreId=document.getElementById("genreId");
        var country=document.getElementById("country");
        var beginYear=document.getElementById("beginYear");
        var endYear=document.getElementById("endYear");
        var error=false;
        if (title.value==null || title.length==0
            || title.value.length > <%= Artist.TITLE_LENGTH %>) {
            error=true;
            title.style.border='1px solid #f00';
        } else {
            title.style.border='';
        }
        if (country.value!=null && country.value.length > <%= Artist.COUNTRY_LENGTH %>) {
            error=true;
            country.style.border='1px solid #f00';
        } else {
            country.style.border='';
        }
        var i=parseInt(genreId.value);
        if (genreId.value=="" || isNaN(i) || i<=0) {
            error=true;
            genreId.style.border='1px solid #f00';
        } else {
            genreId.style.border='';
        }
        i=parseInt(beginYear.value);
        var year=new Date().getYear()+1900;
        if (beginYear.value!="" && isNaN(i) || i<=1000 || i>year) {
            error=true;
            beginYear.style.border='1px solid #f00';
        } else {
            beginYear.style.border='';
        }
        i=parseInt(endYear.value);
        if (endYear.value!="" && isNaN(i) || i<=1000 || i>year) {
            error=true;
            endYear.style.border='1px solid #f00';
        } else {
            endYear.style.border='';
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
%>
<storage:manager/>
<% if(add){%>
<b><%= messages.getString("admin.forms.artists.add") %></b><br /><br/>
<form method="post" action="/admin/artist" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="add"/>
    <%= messages.getString("admin.forms.artists.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title" />
    <small><%= messages.getString("admin.forms.artists.title.notice") %></small><br />
    <%= messages.getString("admin.forms.artists.genreId") %><span class="red">*</span>:
    <!--<input type="text" name="genreId" id="genreId" maxlength="6" />-->
    <select name="genreId" id="genreId">
        <storage:genres end="20"
                        message="<input type=\"text\" name=\"genreId\" id=\"genreId\" maxlength=\"6\"">
            <storage:genre>
                <option value="<storage:field name="id"/>">
                    <storage:field name="title"/>
                </option>
            </storage:genre>
        </storage:genres>
    </select><br/>
    <small><%= messages.getString("admin.forms.genres.add") %></small><br />
    <%= messages.getString("admin.forms.artists.country") %>:
    <input type="text" name="country" id="country" />
    <small><%= messages.getString("admin.forms.artists.country.notice") %></small><br />
    <%= messages.getString("admin.forms.artists.beginYear") %>:
    <input type="text" name="beginYear" id="beginYear" /><br />
    <%= messages.getString("admin.forms.artists.endYear") %>:
    <input type="text" name="endYear" id="endYear" /><br />
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<%} else {%>
<storage:artist keyId="<%= id %>"
message="<%= messages.getString("messages.artist.lost")%>">
<b><%= messages.getString("admin.forms.artists.edit") %></b><br /><br/>
<form method="post" action="/admin/artist" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="save"/>
    <input type="hidden" name="id" value="<storage:field name="genre.id"/>"/>
    <%= messages.getString("admin.forms.artists.title") %><span class="red">*</span>:
    <input type="text" name="title" id="title" value="<storage:field name="title"/>" />
    <small><%= messages.getString("admin.forms.artists.title.notice") %></small><br />
    <%= messages.getString("admin.forms.artists.genreId") %><span class="red">*</span>:
    <input type="text" name="genreId" id="genreId" maxlength="6" value="<storage:field name="genre.id"/>"/><br />
    <%= messages.getString("admin.forms.artists.country") %>:
    <input type="text" name="country" id="country" value="<storage:field name="country"/>" />
    <small><%= messages.getString("admin.forms.artists.country.notice") %></small><br />
    <%= messages.getString("admin.forms.artists.beginYear") %>:
    <input type="text" name="beginYear" id="beginYear" value="<storage:field name="beginYear"/>" /><br />
    <%= messages.getString("admin.forms.artists.endYear") %>:
    <input type="text" name="endYear" id="endYear" value="<storage:field name="endYear"/>" /><br />
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
</storage:artist>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<% }
}%>