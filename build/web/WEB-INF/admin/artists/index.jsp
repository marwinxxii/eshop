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
<b><bundle:message key="admin.forms.artists.view"/></b><br/><br/>
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
                <b><bundle:message key="admin.forms.artists.genreId"/></b>
            </td>
            <td width="45%">
                <b><bundle:message key="admin.forms.artists.title"/></b>
            </td>
            <td width="20%">
                <b><bundle:message key="forms.actions"/></b>
            </td>
        </tr>
        <storage:manager/>
        <storage:artists end="20"
        message="<%= "<tr><td colspan=4>"+messages.getString("messages.artists.notfound")+"</td></tr>" %>">
            <storage:artist>
                <tr>
                    <td class="column"><input type="checkbox" class="record"
                               value="<storage:field name="id"/>"/></td>
                    <td class="column">
                        <storage:field name="id"/>
                    </td>
                    <td class="column">
                        <storage:field name="genre.title"/> (<storage:field name="genre.id"/>)
                    </td>
                    <td class="column">
                        <storage:field name="title"/>
                    </td>
                    <td>
                        <a href="/admin/artists.jsp?act=edit&id=<storage:field name="id"/>">
                            <bundle:message key="forms.edit"/>
                        </a>
                        <!--<a href="/admin/artist?act=del&ids=<storage:field name="id"/>"
                           onclick="return confirm('<bundle:message key="confirm"/>')">
                            <bundle:message key="forms.delete"/>
                        </a>-->
                    </td>
                </tr>
            </storage:artist>
        </storage:artists>
    </table><br/>
<input type="submit" value="<bundle:message key="forms.delete"/>"
       onclick="deleteRecords(event)"/>
</form>
<b><a href="/admin/artists.jsp?act=add"><bundle:message key="admin.forms.artists.add"/></a></b>