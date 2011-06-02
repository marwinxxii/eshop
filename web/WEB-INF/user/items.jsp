<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>
<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
%>
<storage:record entity="Item" identity="last" message="<%= messages.getString("messages.items.notfound") %>">
    <table width="100%" cellpadding="10">
        <tr>
            
        </tr>
    </table>
</storage:record>