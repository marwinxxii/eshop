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
    out.print("<h2>"+messages.getString("messages.genre.lost")+"</h2>");
    return;
}
%>
<storage:genre keyId="<%= id %>"
message="<%= "<h2>"+messages.getString("messages.genre.lost")+"</h2>" %>">
    <h1><storage:field name="title"/></h1><br/>
    <span>
        <storage:field name="description"/>
    </span><br/><br/>
    <b><bundle:message key="user.forms.genres.artists"/>:</b>
    <ul>
        <storage:artists genreId="<%= id %>">
            <storage:artist>
                <li>
                    <h3>
                        <a href="/artists.jsp?id=<storage:field name="id"/>">
                            <storage:field name="title"/>
                        </a>
                    </h3>
                </li>
            </storage:artist>
        </storage:artists>
    </ul>
</storage:genre>