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
<b><%= messages.getString("admin.forms.items.view") %></b><br/><br/>
<form method="post" action="/admin/item">
    <input type="hidden" name="act" value="del"/>
    <input type="hidden" id="ids" name="ids"/>
    <table cellpadding="0" cellspacing="0" class="records">
        <tr style="background-color: #ccc">
            <td width="10px"><input type="checkbox" title="Select all" onclick="selectAll()"/></td>
            <td>
                <b>ID</b>
            </td>
            <td width="10%">
                <b><%= messages.getString("admin.forms.items.mediaType") %></b>
            </td>
            <td width="10%">
                <b><%= messages.getString("admin.forms.items.format") %></b>
            </td>
            <td width="30%">
                <b><%= messages.getString("admin.forms.items.labelId") %></b>
            </td>
            <td width="30%">
                <b><%= messages.getString("admin.forms.items.title") %></b>
            </td>
            <td width="10%">
                <b><%= messages.getString("forms.actions") %></b>
            </td>
        </tr>
        <storage:manager/>
        <storage:items end="20"
        message="<%= "<tr><td colspan=4>"+messages.getString("messages.items.notfound")+"</td></tr>" %>">
            <storage:item>
                <tr>
                    <td class="column"><input type="checkbox" class="record"
                               value="<storage:field name="id"/>"/></td>
                    <td class="column">
                        <storage:field name="id"/>
                    </td>
                    <td class="column">
                        <storage:field name="mediaType"/>
                    </td>
                    <td class="column">
                        <storage:field name="format"/>
                    </td>
                    <td class="column">
                        <storage:field name="label.title"/> (<storage:field name="label.id"/>)
                    </td>
                    <td class="column">
                        <storage:field name="title"/>
                    </td>
                    <td>
                        <a href="/admin/items.jsp?act=edit&id=<storage:field name="id"/>">
                            <%= messages.getString("forms.edit") %>
                        </a>
                        <!--<a href="/admin/item?act=del&ids=<storage:field name="id"/>"
                           onclick="return confirm('<%= messages.getString("confirm") %>')">
                            <%= messages.getString("forms.delete") %>
                        </a>-->
                    </td>
                </tr>
            </storage:item>
        </storage:items>
    </table><br/>
<input type="submit" value="<%= messages.getString("forms.delete") %>"
       onclick="deleteRecords(event)"/>
</form>
<b><a href="/admin/items.jsp?act=add"><%= messages.getString("admin.forms.items.add") %></a></b>