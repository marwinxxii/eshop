<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
%>
<script type="text/javascript">
    var confirmMessage='<%= messages.getString("confirm") %>';
</script>
<script type="text/javascript" src="/admin/static/main.js"></script>
<b><%= messages.getString("admin.forms.artists.view") %></b><br/><br/>
<form method="post" action="/admin/artist">
    <input type="hidden" name="act" value="del"/>
    <input type="hidden" id="ids" name="ids"/>
    <table cellpadding="0" cellspacing="0" class="records">
        <tr style="background-color: #ccc">
            <td width="10px"><input type="checkbox" title="Select all" onclick="selectAll()"/></td>
            <td>
                <b>ID</b>
            </td>
            <td width="30%">
                <b><%= messages.getString("admin.forms.artists.genreId") %></b>
            </td>
            <td width="45%">
                <b><%= messages.getString("admin.forms.artists.title") %></b>
            </td>
            <td width="20%">
                <b>Actions</b>
            </td>
        </tr>
        <storage:record identity="last" entity="Artist"
        message="<%= "<tr><td colspan=4>"+messages.getString("messages.artists.notfound")+"</td></tr>" %>">
            <tr>
                <td><input type="checkbox" class="record"
                           value="<storage:artist field="id"/>"/></td>
                <td>
                    <storage:artist field="id"/>
                </td>
                <td>
                    <storage:artist field="genreId"/>
                </td>
                <td>
                    <storage:artist field="title"/>
                </td>
                <td>
                    <a href="/admin/artists.jsp?act=edit&id=<storage:artist field="id"/>">
                        <%= messages.getString("forms.edit") %>
                    </a>
                    <!--<a href="/admin/artist?act=del&ids=<storage:artist field="id"/>"
                       onclick="return confirm('<%= messages.getString("confirm") %>')">
                        <%= messages.getString("forms.delete") %>
                    </a>-->
                </td>
            </tr>
        </storage:record>
    </table><br/>
<input type="submit" value="<%= messages.getString("forms.delete") %>"
       onclick="deleteRecords(event)"/>
</form>
<b><a href="/admin/artists.jsp?act=add"><%= messages.getString("admin.forms.artists.add") %></a></b>