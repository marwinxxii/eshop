<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="ru.ifmo.eshop.storage.Genre" %>
<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
String sid=request.getParameter("id");
int id=0;
boolean error=false;
if (sid!=null) {
    error=true;
} else {
    try {
        id=Integer.parseInt(sid);
    } catch(NumberFormatException e) {
    }
}
%>