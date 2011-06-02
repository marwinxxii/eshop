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
<b><%= messages.getString("admin.forms.distributors.view") %></b><br/><br/>
<form method="post" action="/admin/distributor">
    <input type="hidden" name="act" value="del"/>
    <input type="hidden" id="ids" name="ids"/>
    <table cellpadding="0" cellspacing="0" class="records">
        <tr style="background-color: #ccc">
            <td width="10px"><input type="checkbox" title="Select all" onclick="selectAll()"/></td>
            <td>
                <b>ID</b>
            </td>
            <td width="10%">
                <b><%= messages.getString("admin.forms.distributors.type") %></b>
            </td>
            <td width="50%">
                <b><%= messages.getString("admin.forms.distributors.title") %></b>
            </td>
            <td width="20%">
                <b><%= messages.getString("admin.forms.distributors.country") %></b>
            </td>
            <td width="10%">
                <b><%= messages.getString("forms.actions") %></b>
            </td>
        </tr>
        <storage:record identity="last" entity="Distributor"
        message="<%= "<tr><td colspan=4>"+messages.getString("messages.distributors.notfound")+"</td></tr>" %>">
            <tr>
                <td class="column"><input type="checkbox" class="record"
                           value="<storage:distributor field="id"/>"/></td>
                <td class="column">
                    <storage:distributor field="id"/>
                </td>
                <td class="column">
                    <storage:distributor field="type"/>
                </td>
                <td class="column">
                    <storage:distributor field="title"/>
                </td>
                <td class="column">
                    <storage:distributor field="country"/>
                </td>
                <td>
                    <a href="/admin/distributors.jsp?act=edit&id=<storage:distributor field="id"/>">
                        <%= messages.getString("forms.edit") %>
                    </a>
                    <!--<a href="/admin/distributor?act=del&ids=<storage:distributor field="id"/>"
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
<b><a href="/admin/distributors.jsp?act=add"><%= messages.getString("admin.forms.distributors.add") %></a></b>