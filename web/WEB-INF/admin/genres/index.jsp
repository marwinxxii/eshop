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
<b><%= messages.getString("admin.forms.genres.view") %></b><br/><br/>
<form method="post" action="/admin/genre">
    <input type="hidden" name="act" value="del"/>
    <input type="hidden" id="ids" name="ids"/>
    <table cellpadding="0" cellspacing="0" class="records">
        <tr style="background-color: #ccc">
            <td width="10px"><input type="checkbox" title="TODO" onclick="selectAll()"/></td>
            <td>
                <b>ID</b>
            </td>
            <td width="30%">
                <b><%= messages.getString("admin.forms.genres.title") %></b>
            </td>
            <td width="45%">
                <b><%= messages.getString("admin.forms.genres.desc") %></b>
            </td>
            <td width="20%">
                <b><%= messages.getString("forms.actions") %></b>
            </td>
        </tr>
        <storage:record identity="last" entity="Genre"
        message="<%= "<tr><td colspan=4>"+messages.getString("messages.genres.notfound")+"</td></tr>" %>">
            <tr>
                <td class="column"><input type="checkbox" class="record"
                           value="<storage:genre field="id"/>"/></td>
                <td class="column">
                    <storage:genre field="id"/>
                </td>
                <td class="column">
                    <storage:genre field="title"/>
                </td>
                <td class="column">
                    <storage:genre field="description" length="40"/>
                </td>
                <td class="column">
                    <a href="/admin/genres.jsp?act=edit&id=<storage:genre field="id"/>">
                        <%= messages.getString("forms.edit") %>
                    </a>
                    <!--<a href="/admin/genre?act=del&id=<storage:genre field="id"/>"
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
<b><a href="/admin/genres.jsp?act=add"><%= messages.getString("admin.forms.genres.add") %></a></b>