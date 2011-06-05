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
    out.print("<h2>"+messages.getString("messages.artist.lost")+"</h2>");
    return;
}
%>
<storage:artist keyId="<%= id %>"
message="<%= "<h2>"+messages.getString("messages.item.lost")+"</h2>" %>">
    <h1><storage:field name="title"/></h1>
    <bundle:message key="user.forms.artists.genre"/>:
    <a href="/genres.jsp?id=<storage:field name="genre.id"/>">
        <storage:field name="genre.title"/>
    </a><br/>
    <bundle:message key="user.forms.artists.country"/>:
    <storage:field name="country"/><br/>
    <bundle:message key="user.forms.artists.beginYear"/>:
    <storage:field name="beginYear"/><br/>
    <bundle:message key="user.forms.artists.endYear"/>:
    <storage:field name="endYear"/><br/><br/>
    <b><bundle:message key="user.forms.artists.items"/>:</b><br/><br/>
    <table width="100%">
        <storage:items artistId="<%= id %>"
        message="<%= messages.getString("messages.items.notfound") %>">
            <storage:item>
                <tr>
                    <td width="150px" valign="top">
                        <img src="<storage:field name="cover" message="/static/images/nocover.png"/>"/>
                    </td>
                    <td>
                        <a href="/items.jsp?id=<storage:field name="id"/>">
                            <storage:field name="title"/>
                        </a><br/><br/>
                        TODO<br/>
                        <storage:field name="mediaType"/>,
                        <storage:field name="format"/><br/>
                        <bundle:message key="user.forms.items.price"/>:
                        <storage:field name="price"/> TODO.
                        <a href="#"><bundle:message key="user.forms.cart.add"/></a>
                        <br/><br/>
                    </td>
                </tr>
            </storage:item>
        </storage:items>
    </table>
</storage:artist>

