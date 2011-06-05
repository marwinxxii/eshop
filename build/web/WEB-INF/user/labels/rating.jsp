<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<%@ page import="java.util.ResourceBundle" %>
<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
%>
<h1><bundle:message key="user.forms.labels.rating"/></h1><br/>
<ol>
    <storage:labels rating="true">
        <storage:label>
            <li>
                <h2>
                    <a href="/labels.jsp?id=<storage:field name="id"/>">
                        <storage:field name="title"/>
                    </a>
                </h2>
            </li>
        </storage:label>
    </storage:labels>
</ol>