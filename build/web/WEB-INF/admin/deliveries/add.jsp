<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="ru.ifmo.eshop.storage.Distributor" %>
<%
ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
String act=request.getParameter("act");
boolean add=true;
boolean error=false;
int id=0;
if (act!=null && act.equals("edit")) {
    if (request.getParameter("id")!=null) {
        add=false;
        try {
            id=Integer.parseInt(request.getParameter("id"));
        } catch(NumberFormatException e) {
            error=true;
        }
    } else {
        error=true;
    }
}
if (error) {%>
    <h1><%= messages.getString("messages.error.id") %></h1>
<%} else {
%>
<storage:manager/>
<script type="text/javascript">
    var itemCount=0;
    var items;
    var editMessage='<%= messages.getString("forms.edit") %>';

    function onSubmit(event) {
        var distId=document.getElementById("distributorId");
        var orderDate=document.getElementById("orderDate");
        var deliverDate=document.getElementById("deliverDate");
        var error=false;
        var i=parseInt(distId.value);
        if (distId.value=="" || isNaN(i) || i<=0) {
            distId.style.border='1px solid #f00';
            error=true;
        } else {
            distId.style.border='';
        }
        i=Date.parse(orderDate.value);
        if (orderDate.value=="" || isNaN(i)) {
            orderDate.style.border='1px solid #f00';
            error=true;
        } else {
            orderDate.style.border='';
        }
        i=Date.parse(deliverDate.value);
        if (deliverDate.value=="" || isNaN(i)) {
            deliverDate.style.border='1px solid #f00';
            error=true;
        } else {
            deliverDate.style.border='';
        }
        var itemsField=document.getElementById('items');
        itemsField.value='';
        for(var k=0;k<items.length;k++) {
            if (items[k]==null) continue;
            itemsField.value+=items[k].id+';'+items[k].amount+';'+items[k].price;
            if (k!=items.length-1) itemsField.value+=',';
        }
        if (error) {
            event.preventDefault();
        }
    }

    <% if (!add) {%>
    items=[
    <storage:delivery keyId="<%= id %>">
    <storage:deliveryList>
        <storage:deliveryItem>
    {
        id:<storage:field name="item.id"/>,
        amount:<storage:field name="amount"/>,
        price:<storage:field name="price"/>
    },
        </storage:deliveryItem>
    </storage:deliveryList>
    </storage:delivery>
    null]
    <% } else { %>
    items=[];
    <% } %>
    function addItem() {
        var itemId=document.getElementById("itemId");
        var amount=document.getElementById("amount");
        var price=document.getElementById("price");
        var error=false;
        var id,a,p;
        var i=parseInt(itemId.value);
        if (itemId.value=="" || isNaN(i) || i<=0) {
            //alert('Wrong item id');
            itemId.style.border='1px solid #f00';
            error=true;
        } else {
            id=i;
            itemId.style.border='';
        }
        i=parseInt(amount.value);
        if (amount.value=="" || isNaN(i) || i<=0) {
            //alert('Wrong amount of items');
            amount.style.border='1px solid #f00';
            error=true;
        } else {
            a=i;
            amount.style.border='';
        }
        i=parseFloat(price.value);
        if (price.value=="" || isNaN(i) || i<=0) {
            //alert('Wrong price');
            price.style.border='1px solid #f00';
            error=true;
        } else {
            p=i;
            price.style.border='';
        }
        if (!error) {
            items.push({
                id:id,
                amount:a,
                price:p
            });
        }
        var itemsTable=document.getElementById('itemsTable').getElementsByTagName("TBODY")[0];
        var tr=document.createElement('tr');
        var td=document.createElement('td');
        td.innerHTML=id;
        tr.appendChild(td);
        td=document.createElement('td');
        td.innerHTML=a;
        tr.appendChild(td);
        td=document.createElement('td');
        td.innerHTML=p;
        tr.appendChild(td);
        td=document.createElement('td');
        td.innerHTML='<a href=#>'+editMessage+'</a>';
        tr.appendChild(td);
        itemsTable.appendChild(tr);
    }
</script>
<%
    if(add){%>
<b><%= messages.getString("admin.forms.deliveries.add") %></b><br /><br/>
<form method="post" action="/admin/delivery" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="add"/>
    <%= messages.getString("admin.forms.deliveries.distributor") %><span class="red">*</span>:
    <!--<input type="text" name="distributorId" id="distributorId"/><br/>-->
    <select name="distributorId" id="distributorId">
        <storage:distributors end="20"
                        message="<input type=\"text\" name=\"distributorId\" id=\"distributorId\" maxlength=\"6\"">
            <storage:distributor>
                <option value="<storage:field name="id"/>">
                    <storage:field name="title"/>
                </option>
            </storage:distributor>
        </storage:distributors>
    </select><br/>
    <%= messages.getString("admin.forms.deliveries.orderDate") %><span class="red">*</span>:
    <input type="text" name="orderDate" id="orderDate"/>
    <small><%= messages.getString("admin.forms.deliveries.orderDate.notice") %></small><br/>
    <%= messages.getString("admin.forms.deliveries.deliverDate") %><span class="red">*</span>:
    <input type="text" name="deliverDate" id="deliverDate"/>
    <small><%= messages.getString("admin.forms.deliveries.deliverDate.notice") %></small>
    <br/><br/>
    <%= messages.getString("admin.forms.deliveries.items") %>
    <span class="red">*</span><br/>

    <table id="itemsTable" style="width:100%" class="records">
        <tr style="background-color: #ccc">
            <td width="50%">
                <b><%= messages.getString("admin.forms.deliveries.items.id") %></b>
            </td>
            <td width="15%">
                <b><%= messages.getString("admin.forms.deliveries.items.amount") %></b>
            </td>
            <td width="15%">
                <b><%= messages.getString("admin.forms.deliveries.items.price") %></b>
            </td>
            <td width="10%">
                <b><%= messages.getString("forms.actions") %></b>
            </td>
        </tr>
    </table>
    <table style="width:100%" class="records">
        <tr>
            <td width="50%">
                <input type="textbox" id="itemId"/>
            </td>
            <td width="15%">
                <input type="textbox" id="amount" style="width:70%;"/>
            </td>
            <td width="15%">
                <input type="textbox" id="price" style="width:70%;"/>
            </td>
            <td width="10%">
                <input type="button" onclick="addItem()"
                       value="<%= messages.getString("admin.forms.deliveries.items.add") %>"/>
            </td>
        </tr>
    </table>
    <br/>
    <input type="hidden" value="" id="items" name="items"/>
    <input type="submit" value="<%= messages.getString("forms.submit") %>"/>
</form>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<%} else {%>
<storage:delivery keyId="<%= id %>"
message="<%= messages.getString("messages.delivery.lost")%>">
<b><%= messages.getString("admin.forms.deliveries.edit") %></b><br /><br/>
<form method="post" action="/admin/delivery" onsubmit="onSubmit(event);">
    <input type="hidden" name="act" value="save"/>
    <input type="hidden" name="id" value="<storage:field name="id"/>"/>

    <%= messages.getString("admin.forms.deliveries.distributor") %><span class="red">*</span>:
    <input type="text" name="distributorId" id="distributorId"
           value="<storage:field name="distributor.id"/>"/>
    <br/>
    <%= messages.getString("admin.forms.deliveries.orderDate") %><span class="red">*</span>:
    <input type="text" name="orderDate" id="orderDate"
           value="<storage:field name="orderDate"/>"/>
    <small><%= messages.getString("admin.forms.deliveries.orderDate.notice") %></small>
    <br/>
    <%= messages.getString("admin.forms.deliveries.deliverDate") %><span class="red">*</span>:
    <input type="text" name="deliverDate" id="deliverDate"
           value="<storage:field name="deliverDate"/>"/>
    <small><%= messages.getString("admin.forms.deliveries.deliverDate.notice") %></small><br/>
    <%= messages.getString("admin.forms.deliveries.items") %>
    <span class="red">*</span>:<br/><br/>

    <table id="itemsTable" style="width:100%" class="records">
        <tr style="background-color: #ccc">
            <td width="50%">
                <b><%= messages.getString("admin.forms.deliveries.items.id") %></b>
            </td>
            <td width="15%">
                <b><%= messages.getString("admin.forms.deliveries.items.amount") %></b>
            </td>
            <td width="15%">
                <b><%= messages.getString("admin.forms.deliveries.items.price") %></b>
            </td>
            <td width="10%">
                <b><%= messages.getString("forms.actions") %></b>
            </td>
        </tr>
        <storage:deliveryList>
            <storage:deliveryItem>
                <tr>
                    <td><storage:field name="item.title"/> (<storage:field name="item.id"/>)</td>
                    <td><storage:field name="amount"/></td>
                    <td><storage:field name="price"/></td>
                    <td><a href="#"><%= messages.getString("forms.edit") %></a></td>
                </tr>
            </storage:deliveryItem>
        </storage:deliveryList>
    </table>
    <table style="width:100%" class="records">
        <tr>
            <td width="50%">
                <input type="textbox" id="itemId"/>
            </td>
            <td width="15%">
                <input type="textbox" id="amount" style="width:70%;"/>
            </td>
            <td width="15%">
                <input type="textbox" id="price" style="width:70%;"/>
            </td>
            <td width="10%">
                <input type="button" onclick="addItem()"
                       value="<%= messages.getString("admin.forms.deliveries.items.add") %>"/>
            </td>
        </tr>
    </table>
    <input type="hidden" value="" id="items" name="items"/>
    <input type="submit" value="<%= messages.getString("forms.save") %>"/>
</form>
</storage:delivery>
<br/><small><%= messages.getString("forms.mandatory") %></small>
<% }
}%>