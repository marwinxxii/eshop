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
<b><%= messages.getString("admin.forms.labels.view") %></b><br/><br/>
<form method="post" action="/admin/label">
    <input type="hidden" name="act" value="del"/>
    <input type="hidden" id="ids" name="ids"/>
    <table cellpadding="0" cellspacing="0" class="records">
        <tr style="background-color: #ccc">
            <td width="10px"><input type="checkbox" title="TODO" onclick="selectAll()"/></td>
            <td>
                <b>ID</b>
            </td>
            <td width="30%">
                <b><%= messages.getString("admin.forms.labels.title") %></b>
            </td>
            <td width="45%">
                <b><%= messages.getString("admin.forms.labels.country") %></b>
            </td>
            <td width="20%">
                <b>Actions</b>
            </td>
        </tr>
        <storage:record identity="last" entity="Label"
        message="<%= "<tr><td colspan=4>"+messages.getString("messages.labels.notfound")+"</td></tr>" %>">
            <tr>
                <td><input type="checkbox" class="record"
                           value="<storage:label field="id"/>"/></td>
                <td>
                    <storage:label field="id"/>
                </td>
                <td>
                    <storage:label field="title"/>
                </td>
                <td>
                    <storage:label field="country"/>
                </td>
                <td>
                    <a href="/admin/labels.jsp?act=edit&id=<storage:label field="id"/>">
                        <%= messages.getString("forms.edit") %>
                    </a>
                    <!--<a href="/admin/label?act=del&ids=<storage:label field="id"/>"
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
<b><a href="/admin/labels.jsp?act=add"><%= messages.getString("admin.forms.labels.add") %></a></b>