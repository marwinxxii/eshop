<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ page import="ru.ifmo.eshop.storage.StorageManager" %>
<%@ page import="ru.ifmo.eshop.storage.Item" %>
<%@ page import="java.util.List" %>
<jsp:include page="WEB-INF/common.jsp"/>
        <div id="left">
            <jsp:include page="WEB-INF/menu.jsp"/>
        </div>
        <div id="right">
<%
    List<Item> items=null;
    try {
        StorageManager sm = new StorageManager("benderhost", 1521, "eshop", "eshop");
        items=sm.getLastItems();
        sm.close();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        for (StackTraceElement el:e.getStackTrace()) {
            out.println(el.toString());
        }
    }
    if (items==null || items.isEmpty()) {
        //if (items==null) out.println("null");
        %>
        <h1><%= messages.getString("notFound") %></h1>
        <%
    } else {
        %>
        <b><%= messages.getString("last") %>:</b><br /><br />
        <table width="100%" cellspacing="10">
            <tr>
        <%
        int r=0;
        for (Item i:items) {
            if (r==2) {
                %>
            </tr>
            <tr>
                <%
            }
            String cover=i.getCover();
            //if (cover.isEmpty()) {
                cover="/static/images/nocover.png";
            //}
            %>
            <td valign="top" width="50%">
                <img class="cover" src="<%= cover %>" width="150px" height="150px" alt="cover" />
                <a href="/artist/<%= i.getArtists().get(0).getId()%>"><b><%= i.getArtists().get(0).getTitle()%></b></a><br />
                <a href="/item/<%=i.getId()%>"><%=i.getTitle()%></a><br />
                <%=i.getFormat()%><br />
                <a href="#"><small>Добавить в корзину</small></a>
            </td>
            <%
        }
        %>
            </tr>
        </table>
        <%
    }
%>
        </div>
    </div>
</body>
</html>
