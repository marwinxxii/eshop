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
<b><bundle:message key="admin.forms.deliveries.view"/></b><br/><br/>
<form method="post" action="/admin/delivery">
    <input type="hidden" name="act" value="del"/>
    <input type="hidden" id="ids" name="ids"/>
    <table cellpadding="0" cellspacing="0" class="records">
        <tr style="background-color: #ccc">
            <td width="10px"><input type="checkbox" title="TODO" onclick="selectAll()"/></td>
            <td>
                <b>ID</b>
            </td>
            <td width="50%">
                <b><bundle:message key="admin.forms.deliveries.distributor"/></b>
            </td>
            <td width="25%">
                <b><bundle:message key="admin.forms.deliveries.deliverDate"/></b>
            </td>
            <td>
                <b><bundle:message key="admin.forms.deliveries.size"/></b>
            </td>
            <td width="15%">
                <b><bundle:message key="forms.actions"/></b>
            </td>
        </tr>
        <storage:manager/>
        <storage:deliveries end="20"
        message="<%= "<tr><td colspan=4>"+messages.getString("messages.deliveries.notfound")+"</td></tr>" %>">
            <storage:delivery>
                <tr>
                    <td class="column"><input type="checkbox" class="record"
                               value="<storage:field name="id"/>"/></td>
                    <td class="column">
                        <storage:field name="id"/>
                    </td>
                    <td class="column">
                        <storage:field name="distributor.title"/>
                        (<storage:field name="distributor.id"/>)
                    </td>
                    <td class="column">
                        <storage:field name="deliverDate"/>
                    </td>
                    <td class="column">
                        <storage:field name="size"/>
                    </td>
                    <td>
                        <a href="/admin/deliveries.jsp?act=edit&id=<storage:field name="id"/>">
                            <bundle:message key="forms.edit"/>
                        </a>
                        <!--<a href="/admin/label?act=del&ids=<storage:field name="id"/>"
                           onclick="return confirm('<bundle:message key="forms.delete"/>')">
                        </a>-->
                    </td>
                </tr>
            </storage:delivery>
        </storage:deliveries>
    </table><br/>
<input type="submit" value="<bundle:message key="forms.delete"/>"
       onclick="deleteRecords(event)"/>
</form>
<b><a href="/admin/deliveries.jsp?act=add"><bundle:message key="admin.forms.deliveries.add"/></a></b>