<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>

<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
%>
<script type="text/javascript">
    var selected=false;
    
    function deleteGenres() {
        var genres=document.getElementsByClassName("genre");
        var ids=document.getElementById("ids");
        ids.value='';
        for (var i=0;i<genres.length;i++) {
            if (genres[i].checked) {
                ids.value+=genres[i].value;
            }
            if (i!=genres.length-1) ids.value+=',';
        }
        return confirm('<%= messages.getString("confirm") %>')
    }

    function selectAll() {
        var genres=document.getElementsByClassName("genre");
        for (var i=0;i<genres.length;i++) {
            genres[i].checked=!selected;
        }
        selected=!selected;
    }
</script>
<b><%= messages.getString("admin.forms.genres.view") %></b><br/><br/>
<form method="post" action="/admin/genre">
    <input type="hidden" name="act" value="del"/>
    <input type="hidden" id="ids" name="ids"/>
    <table width="100%" cellpadding="0" cellspacing="0" style="text-align: center">
        <tr style="background-color: #ccc">
            <td width="10px"><input type="checkbox" title="TODO" onclick="selectAll()"/></td>
            <td>
                <b>ID</b>
            </td>
            <td width="30%">
                <b><%= messages.getString("admin.forms.genres.title") %></b>
            </td>
            <td width="45%">
                <b><%= messages.getString("admin.forms.genres.desc") %></b>
            </td>
            <td width="20%">
                <b>Actions</b>
            </td>
        </tr>
        <storage:getGenre identity="last" message="<%= "<tr><td colspan=4>"+messages.getString("messages.genres.notfound")+"</td></tr>" %>">
            <tr style="background-color: #eee">
                <td><input type="checkbox" class="genre"
                           name="genre<storage:genre field="id"/>"
                           value="<storage:genre field="id"/>"/></td>
                <td>
                    <storage:genre field="id"/>
                </td>
                <td>
                    <storage:genre field="title"/>
                </td>
                <td>
                    <storage:genre field="description" length="40"/>
                </td>
                <td>
                    <a href="/admin/genres.jsp?act=edit&id=<storage:genre field="id"/>">
                        <%= messages.getString("forms.edit") %>
                    </a>
                    <a href="/admin/genre?act=del&id=<storage:genre field="id"/>"
                       onclick="return confirm('<%= messages.getString("confirm") %>')">
                        <%= messages.getString("forms.delete") %>
                    </a>
                </td>
            </tr>
        </storage:getGenre>
    </table><br/>
<input type="submit" value="<%= messages.getString("forms.delete") %>"
       onclick="deleteGenres()"/>
</form>
<b><a href="/admin/genres.jsp?act=add"><%= messages.getString("admin.forms.genres.add") %></a></b>