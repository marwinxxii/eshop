<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" session="true"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<%@ page import="java.util.ResourceBundle" %>
<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
%>
<b><bundle:message key="user.forms.items.view"/>:</b><br/><br/>
<table class="itemsTable" cellspacing="10">
    <storage:items end="6"
    message="<%= "<td><h1>"+
            messages.getString("messages.items.notfound")+"</h1></td>" %>">
        <tr>
            <storage:item>
                <td>
                    <img class="cover" src="<storage:field name="cover" message="/static/images/nocover.png"/>"
                         width="150px" height="150px" alt="cover" />
                    <a href="/artists.jsp?id=<storage:field name="artist.id"/>">
                        <b><storage:field name="artist.title"/></b>
                    </a><br />
                    <a href="/items.jsp?id=<storage:field name="id"/>">
                        <storage:field name="title"/>
                        [<storage:field name="year"/>]
                    </a><br />
                    <storage:field name="mediaType"/>,
                    <storage:field name="format"/><br /><br />
                    <a href="javascript:" onclick="addToCart(<storage:field name="id"/>)">
                        <small><bundle:message key="user.forms.cart.add"/></small>
                    </a>
                </td>
            </storage:item>
            <storage:item message="<td>&nbsp;</td>">
                <td>
                    <img class="cover" src="<storage:field name="cover" message="/static/images/nocover.png"/>"
                         width="150px" height="150px" alt="cover" />
                    <a href="/artists.jsp?id=<storage:field name="artist.id"/>">
                        <b><storage:field name="artist.title"/></b>
                    </a><br />
                    <a href="/items.jsp?id=<storage:field name="id"/>">
                        <storage:field name="title"/>
                        [<storage:field name="year"/>]
                    </a><br />
                    <storage:field name="mediaType"/>,
                    <storage:field name="format"/><br /><br />
                    <a href="javascript:" onclick="addToCart(<storage:field name="id"/>)">
                        <small><bundle:message key="user.forms.cart.add"/></small>
                    </a>
                </td>
            </storage:item>
        </tr>
    </storage:items>
</table>