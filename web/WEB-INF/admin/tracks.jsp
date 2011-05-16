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
<b><%= messages.getString("admin.forms.tracks.view") %></b><br/><br/>
<form method="post" action="/admin/track">
    <input type="hidden" name="act" value="del"/>
    <input type="hidden" id="ids" name="ids"/>
    <table cellpadding="0" cellspacing="0" class="records">
        <tr style="background-color: #ccc">
            <td width="10px"><input type="checkbox" title="Select all" onclick="selectAll()"/></td>
            <td>
                <b>ID</b>
            </td>
            <td width="10%">
                <b><%= messages.getString("admin.forms.tracks.itemId") %></b>
            </td>
            <td width="10%">
                <b><%= messages.getString("admin.forms.tracks.artistId") %></b>
            </td>
            <td width="10%">
                <b><%= messages.getString("admin.forms.tracks.trackNumber") %></b>
            </td>
            <td width="50%">
                <b><%= messages.getString("admin.forms.tracks.title") %></b>
            </td>
            <td width="10%">
                <b>Actions</b>
            </td>
        </tr>
        <storage:record identity="last" entity="Track"
        message="<%= "<tr><td colspan=4>"+messages.getString("messages.tracks.notfound")+"</td></tr>" %>">
            <tr>
                <td><input type="checkbox" class="record"
                           value="<storage:track field="id"/>"/></td>
                <td>
                    <storage:track field="id"/>
                </td>
                <td>
                    <storage:track field="itemId"/>
                </td>
                <td>
                    <storage:track field="artistId"/>
                </td>
                <td>
                    <storage:track field="trackNumber"/>
                </td>
                <td>
                    <storage:track field="title"/>
                </td>
                <td>
                    <a href="/admin/tracks.jsp?act=edit&id=<storage:track field="id"/>">
                        <%= messages.getString("forms.edit") %>
                    </a>
                    <!--<a href="/admin/track?act=del&ids=<storage:track field="id"/>"
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
<b><a href="/admin/tracks.jsp?act=add"><%= messages.getString("admin.forms.tracks.add") %></a></b>