<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<%@ page import="java.util.ResourceBundle" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
%>
<script type="text/javascript">
    var confirmMessage='<bundle:message key="confirm"/>';
</script>
<script type="text/javascript" src="/admin/static/main.js"></script>
<b><bundle:message key="admin.forms.labels.view"/></b><br/><br/>
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
                <b><bundle:message key="admin.forms.labels.title"/></b>
            </td>
            <td width="45%">
                <b><bundle:message key="admin.forms.labels.country"/></b>
            </td>
            <td width="20%">
                <b><bundle:message key="forms.actions"/></b>
            </td>
        </tr>
        <storage:record identity="last" entity="Label"
        message="<%= "<tr><td colspan=4>"+messages.getString("messages.labels.notfound")+"</td></tr>" %>">
            <tr>
                <td class="column"><input type="checkbox" class="record"
                           value="<storage:label field="id"/>"/></td>
                <td class="column">
                    <storage:label field="id"/>
                </td>
                <td class="column">
                    <storage:label field="title"/>
                </td>
                <td class="column">
                    <storage:label field="country"/>
                </td>
                <td>
                    <a href="/admin/labels.jsp?act=edit&id=<storage:label field="id"/>">
                        <bundle:message key="forms.edit"/>
                    </a>
                    <!--<a href="/admin/label?act=del&ids=<storage:label field="id"/>"
                       onclick="return confirm('<bundle:message key="forms.delete"/>')">
                    </a>-->
                </td>
            </tr>
        </storage:record>
    </table><br/>
<input type="submit" value="<bundle:message key="forms.delete"/>"
       onclick="deleteRecords(event)"/>
</form>
<b><a href="/admin/labels.jsp?act=add"><bundle:message key="admin.forms.labels.add"/></a></b>