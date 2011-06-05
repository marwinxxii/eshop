<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="ru.ifmo.eshop.storage.StorageManager" %>

<script type="text/javascript">
    var ids=[];
    var selected=false;

    function deleteRecords(event) {
        if (!confirm(confirmMessage)) {
            event.preventDefault();
        } else {
            var records=document.getElementsByClassName("record");
            var ids=document.getElementById("ids");
            ids.value='';
            var newCart=[];
            var i=0;
            var tb=document.getElementById('cartItems');
            var newtb=document.createElement('table');
            for (i=0;i<records.length;i++) {
                var tr=records[i].parentNode.parentNode;
                if (tr.className!='deleteMe') {
                    newtb.appendChild(tr);
                } else {
                    if (records[i].checked) {
                        ids.value+=records[i].value;
                    } else {
                        newCart.push(cart[i]);
                        newtb.appendChild(tr);
                    }
                    if (i!=records.length-1) ids.value+=',';
                }
            }
            tb.innerHTML=newtb.innerHTML;
            cart=newCart;
            var temp='';
            for (i=0;i<cart.length;i++) {
                temp+=cart[i].item+'-'+cart[i].count;
                if (i!=cart.length-1) {
                    temp+='_';
                }
            }
            nano.cookie.write('cart',temp,
                    nano.time(nano.time()+(30*60*1000), 'UTC'),'/');
            temp=document.getElementById('cart');
            temp.innerHTML=cartLink+'<span style="font-size:80%"> ('+cart.length+')</span>';
            //nano.cookie.write('cart','',nano.time(nano.time()+(30*60*1000),'UTC'),'/');
        }
    }

    function selectAll() {
        selected=!selected;
        var records=document.getElementsByClassName("record");
        for (var i=0;i<records.length;i++) {
            records[i].checked=selected;
        }
    }
</script>
<h1><bundle:message key="user.forms.cart"/></h1>
<table class="cart" cellspacing="0" id="cartItems">
    <tr style="background:#CCC;">
        <td width="20px"><input type="checkbox" onclick="selectAll()"></td>
        <td width="50%" align="center">
            <b><bundle:message key="user.forms.cart.items.title"/></b>
        </td>
        <td align="center">
            <b><bundle:message key="user.forms.cart.items.count"/></b>
        </td>
        <td align="center">
            <b><bundle:message key="user.forms.cart.items.price"/></b>
        </td>
    </tr>
    <%
    ResourceBundle messages=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
    Cookie[] cookies=request.getCookies();
    String idis="";
    if (cookies==null || cookies.length==0) {
        out.print("<tr><td align=\"center\" colspan=\"4\">"+
                    messages.getString("user.forms.cart.empty")+"</td></tr>");
    } else {
        String cart=null;
        for (Cookie c:cookies) {
            if (c.getName().equals("cart")) {
                cart=c.getValue();
                break;
            }
        }
        if (cart==null || cart.isEmpty()) {
            out.print("<tr><td align=\"center\" colspan=\"4\">"+
                    messages.getString("user.forms.cart.empty")+"</td></tr>");
        } else {
            System.err.println("cart:"+cart);
            String[] temp=cart.split("_");
            int[] ids=new int[temp.length];
            int[] counts=new int[temp.length];
            int i=0;
            for (String t :temp) {
                System.err.println("t:"+t);
                ids[i]=Integer.parseInt(t.substring(0,t.indexOf('-')));
                counts[i++]=Integer.parseInt(t.substring(t.indexOf('-')+1));//,t.indexOf(')')));
            }
            int totalCount=0;
            double totalPrice=0;
            StorageManager sm=(StorageManager)
                    pageContext.getAttribute("storageManager",
                    PageContext.REQUEST_SCOPE);
            for (i=0;i<temp.length;i++) {
                totalCount+=counts[i];
                totalPrice+=sm.getPriceOfItem(ids[i]);
                idis+=String.valueOf(ids[i]);
                if (i!=temp.length-1) idis+=',';
                %>
                <storage:item keyId="<%= ids[i] %>">
                    <tr class="deleteMe">
                        <td><input type="checkbox" class="record"></td>
                        <td align="center">
                            <a href="/items.jsp?id=<storage:field name="id"/>">
                                <storage:field name="artist.title"/> -
                                <storage:field name="title"/>
                            </a>
                        </td>
                        <td align="center">
                            <%= counts[i] %>
                        </td>
                        <td align="center">
                            <storage:field name="price"/>
                        </td>
                    </tr>
                </storage:item>
                <%
            }
            %>
            <tr>
                <td>
                    <input type="button"
                           onclick="deleteRecords(event)"
                           value="X"
                           title="<bundle:message key="user.forms.cart.items.delete"/>"/>
                </td>
                <td align="right"><b><bundle:message key="user.forms.cart.items.total"/>:</b></td>
                <td align="center"><b><%= totalCount %></b></td>
                <td align="center"><b><%= totalPrice %></b></td>
            </tr>
            <%
        }
    }
    %>
</table>
<div align="right">
    <form method="post" action="/pay">
        <input type="hidden" value="<%= idis %>" id="ids"/>
        <input type="submit" value="<bundle:message key="user.forms.cart.confirm"/>"/>
    </form>
</div>