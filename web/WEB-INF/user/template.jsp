<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<%@ taglib uri="/WEB-INF/tlds/storage.tld" prefix="storage" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%
boolean loggedIn=false;
String email="";
Cookie[] cookies=request.getCookies();
for (Cookie c:cookies) {
    if (c.getName().equals("email")) {
        loggedIn=true;
        email=c.getValue();
        break;
    }
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Eshop</title>
    <link type="text/css" rel="stylesheet" href="/static/main.css">
    <link type="application/rss+xml" rel="alternate" title="RSS" href="/rss">
    <script type="text/javascript" src="/static/nano.js"></script>
    <script type="text/javascript" src="/static/nano.cookie.js"></script>
    <script type="text/javascript">
        var cart=[];
        var cartLink='<bundle:message key="user.menu.cart"/>';
        var confirmMessage='<bundle:message key="confirm"/>';
        
        nano(function() {
            var temp=nano.cookie.read('cart');
            if (temp!=null && temp!='') {
                temp=temp.split('_');
                for (var i=0;i<temp.length;i++) {
                    var k=temp[i].indexOf('-');
                    cart.push({
                        item:temp[i].substr(0, k),
                        count:temp[i].substr(k+1,temp[i].length-k-1)
                    });
                }
            }
            if (cart.length!=0) {
                document.getElementById('cart').innerHTML=cartLink+
                    '<span style="font-size:80%"> ('+cart.length+')</span>';
            }
            //nano.cookie.write('cart','',nano.time(nano.time()+(30*60*1000),'UTC'),'/');
        });
        function addToCart(item) {
            if (cart==null || cart.length==0) {
                cart=[{
                    item:item,
                    count:1}];
            } else {
                var incart=false;
                for (var i=0;i<cart.length;i++) {
                    if (cart[i].item==item) {
                        cart[i].count++;
                        incart=true;
                        break;
                    }
                }
                if (!incart) {
                    cart.push({
                        item:item,
                        count:1
                    });
                }
            }
            var temp='';
            for (var i=0;i<cart.length;i++) {
                temp+=cart[i].item+'-'+cart[i].count;
                if (i!=cart.length-1) {
                    temp+='_';
                }
            }
            nano.cookie.write('cart',temp,
                    nano.time(nano.time()+(30*60*1000), 'UTC'),'/');
            temp=document.getElementById('cart');
            temp.innerHTML=cartLink+'<span style="font-size:80%"> ('+cart.length+')</span>';
        }
	function validateEmail(elementValue){
		var emailPattern = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		if (!emailPattern.test(elementValue)) {
                    alert('Incorrect email');
			return false;
		} else {
			return true;
		}
	}
	function showLogin() {
		var sw=document.getElementById('loginMenu');
		if (sw.style.visibility=='hidden') {
			sw.style.visibility='visible';
		} else {
			sw.style.visibility='hidden';
		}
	}

	function showSubscribe() {
		var sw=document.getElementById('subscribe');
		if (sw.style.visibility=='hidden') {
			sw.style.visibility='visible';
		} else {
			sw.style.visibility='hidden';
		}
        }
	</script>
  </head>
<storage:manager/>
<body>
  <form action="http://www.google.com/cse" id="cse-search-box">
    <div style="float:left;text-align:left">
      <input name="cx" value="003399418647576449102:egk553zukni" type="hidden">
      <input name="ie" value="UTF-8" type="hidden">
      <input class="gwt-TextBox" name="q" size="31" type="text">
      <input src="/static/images/find.png" name="sa" title="Search" value="Search" type="image">
    </div>
    <a class="topMenuLink" href="/subscribe" title="<bundle:message key="user.subscription.notice"/>" onclick="showSubscribe();return false;">
        <img src="/static/images/email.png"> <bundle:message key="user.subscription"/></a>
    <a class="topMenuLink" href="#"><img src="/static/images/t_mini-b.png"> Eshop в Twitter</a>

    <% if (loggedIn) {%>
    <a class="topMenuLink" href="/user.jsp"><!-- onclick="showLogin();return false;">-->
        <img src="/static/images/user.png"> <%= email %>
    </a>
    <% } else {%>
    <a class="topMenuLink" href="/signup.jsp"><!-- onclick="showLogin();return false;">-->
        <img src="/static/images/user.png"> <bundle:message key="user.signup"/>
    </a>
    <%} %>
  </form>
  <div id="loginMenu" style="visibility:hidden">
	<ul class="loginMenu">
	  <li class="loginMenu"><a href="#" class="black">Google</a></li>
	  <li class="loginMenu"><a href="#" class="black">Yahoo</a></li>
	  <li class="loginMenu"><a href="#" class="black">MySpace</a></li>
	  <li class="loginMenu"><a href="#" class="black">AOL</a></li>
	  <li class="loginMenu"><a href="#">MyOpenID</a></li>
	</ul>
  </div>
<form id="subscribe" method="post" action="/subscribe/new" style="visibility:hidden">
	<bundle:message key="user.email"/>:<br>
	<input name="page" value="/" type="hidden">
	<input id="email" class="gwt-TextBox" name="email" maxlength="50" type="text"><br>
	<input value="<bundle:message key="user.subscribe"/>"
               onclick="return validateEmail(document.getElementById('email').value)" type="submit">
</form>
<div id="leftMenu">
	<ul class="leftMenu">
		<!--<li class="menuHeader"><bundle:message key="user.menu"/></li>-->
		<li class="leftMenu">
                    <a href="/" class="menuLink">
                        <bundle:message key="user.menu.main"/>
                    </a>
                </li>
		<li class="leftMenu">
                    <a href="/artists.jsp" class="menuLink">
                        <bundle:message key="user.menu.artists"/>
                    </a>
                </li>
                <li class="leftMenu">
                    <a href="/labels.jsp" class="menuLink">
                        <bundle:message key="user.menu.labels"/>
                    </a>
                </li>
                <li class="leftMenu">
                    <a href="/genres.jsp" class="menuLink">
                        <bundle:message key="user.menu.genres"/>
                    </a>
                </li>
		<li class="leftMenu">
                    <a href="/cart.jsp" class="menuLink" id="cart">
                        <bundle:message key="user.menu.cart"/>
                    </a>
                </li>
		<!--<li class="leftMenu"><a href="/api" class="menuLink">API</a></li>
		<li class="menuHeader">RSS <img src="/static/feed.png"></li>
		<li class="leftMenu"><a href="/rss/today" class="menuLink">Today</a></li>
		<li class="leftMenu"><a href="/rss/month" class="menuLink">Month</a></li>
		<li class="leftMenu"><a href="/rss/last" class="menuLink">Last added</a></li>
		<li class="leftMenu"><a href="/rss/get" class="menuLink">Other</a></li><br>-->
	</ul>
</div>
<div id="rightMenu">
<div id="mmain">
    <template:get name="content"/>
</div>
</div>
<div id="footer">
	<a href="/" class="black"><bundle:message key="user.menu.main"/></a> |
	<a href="/about#contact" class="black"><bundle:message key="user.menu.feedback"/></a> |
	<a href="/about" class="black"><bundle:message key="user.menu.about"/></a> |
	<a href="/?locale=en" class="black">English</a> |
	<a href="/?locale=ru" class="black">Русский</a>
</div>
</body></html>
