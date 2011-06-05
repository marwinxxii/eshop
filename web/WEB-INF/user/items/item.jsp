<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" session="true"%>
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
    out.print("<h2>"+messages.getString("messages.item.lost")+"</h2>");
    return;
}
%>
<storage:item keyId="<%= id %>"
message="<%= "<h2>"+messages.getString("messages.item.lost")+"</h2>" %>">
    <h1><a href="/artists.jsp?id=<storage:field name="artist.id"/>">
            <storage:field name="artist.title"/></a>
    </h1><br/>
    <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
            <td width="250px" valign="top">
                <img class="cover" src="<storage:field name="cover"/>" alt="cover"/>
            </td>
            <td valign="top">
                <h2 style="margin-top: 0px;">
                    <storage:field name="title"/>
                </h2>
                <bundle:message key="user.forms.items.releaseDate"/>:
                <storage:field name="releaseDate"/><br/>
                <bundle:message key="user.forms.items.label"/>:
                <a href="/labels.jsp?id=<storage:field name="label.id"/>">
                    <storage:field name="label.title"/>
                </a><br/>
                <bundle:message key="user.forms.items.mediaType"/>:
                <storage:field name="mediaType"/><br/>
                <bundle:message key="user.forms.items.format"/>:
                <storage:field name="format"/><br/><br/>
                <bundle:message key="user.forms.items.price"/>:
                <storage:field name="price"/>
                <a href="#"><bundle:message key="user.forms.cart.add"/></a>
                <br/><br/>
                <b><bundle:message key="user.forms.items.tracks"/>:</b><br/>
                <ol>
                    <storage:tracks itemId="<%= id %>"
                    message="<%= messages.getString("messages.tracks.notfound") %>">
                        <storage:track>
                            <li>
                                <storage:field name="title"/>
                                <storage:field name="duration"/>
                            </li>
                        </storage:track>
                    </storage:tracks>
                </ol>
            </td>
        </tr>
    </table>
</storage:item>