<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<%@ page import="java.util.ResourceBundle" %>
<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
boolean error=false;
int id=0;
try {
    id=Integer.parseInt(request.getParameter("id"));
} catch(NumberFormatException e) {
    error=true;
}
if (error) {
    out.print("<h2>"+messages.getString("messages.label.lost")+"</h2>");
    return;
}
%>
<storage:label keyId="<%= id %>"
message="<%= "<h2>"+messages.getString("messages.label.lost")+"</h2>" %>">
    <h1><storage:field name="title"/></h1>
    <bundle:message key="user.forms.labels.country"/>:
    <storage:field name="country"/><br/><br/>

    <b><bundle:message key="user.forms.labels.items"/>:</b><br/><br/>
    <storage:items labelId="<%= id %>"
    message="<%= messages.getString("messages.items.notfound") %>">
        <table width="100%">
            <storage:item>
                <tr>
                    <td width="150px" valign="top">
                        <img src="<storage:field name="cover" message="/static/images/nocvoer.png"/>"
                             width="150px" height="150px"/>
                    </td>
                    <td>
                        <a href="/artists.jsp?id=<storage:field name="artist.id"/>">
                            <storage:field name="artist.title"/>
                        </a> -
                        <a href="/items.jsp?id=<storage:field name="id"/>">
                            <storage:field name="title"/>
                            [<storage:field name="year"/>]
                        </a><br/><br/>
                        TODO<br/>
                        <storage:field name="mediaType"/>,
                        <storage:field name="format"/><br/>
                        <bundle:message key="user.forms.items.price"/>:
                        <storage:field name="price"/> TODO.<br/>
                        <a href="#"><bundle:message key="user.forms.cart.add"/></a>
                    </td>
                </tr>
            </storage:item>
        </table>
    </storage:items>
</storage:label>