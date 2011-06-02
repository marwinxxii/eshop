<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.MissingResourceException" %>
<%@ page import="java.util.Locale" %>
<%
Locale locale = request.getLocale();
String lang = "";
if (request.getParameter("locale") != null) {
    lang = request.getParameter("locale").toLowerCase();
    if (lang.equals("ru")) {
        locale = new Locale("ru", "RU");
        Cookie c = new Cookie("locale", "ru");
        c.setPath("/");
        c.setMaxAge(315360000);
        response.addCookie(c);
    } else {
        lang = "en";
        locale = new Locale("en", "US");
        Cookie c = new Cookie("locale", "en");
        c.setPath("/");
        c.setMaxAge(315360000);
        response.addCookie(c);
    }
} else {
    lang = "en";
    Cookie[] cookies = request.getCookies();
    if (cookies == null) {
        if (locale == null) {
            locale = new Locale("en", "US");
            Cookie c = new Cookie("locale", "en");
            c.setPath("/");
            c.setMaxAge(315360000);
            response.addCookie(c);
        }
    } else {
        boolean cookieSet = false;
        for (Cookie c : cookies) {
            if (c.getName().equals("locale")) {
                if (c.getValue() != null) {
                    lang = c.getValue().toLowerCase();
                    if (lang.equals("ru")) {
                        locale = new Locale("ru", "RU");
                    } else {
                        lang = "en";
                        locale = new Locale("en", "US");
                    }
                    cookieSet = true;
                    break;
                }
            }
        }
        if (!cookieSet) {
            Cookie c = new Cookie("locale", "en");
            c.setPath("/");
            c.setMaxAge(315360000);
            response.addCookie(c);
        }
    }
}
ResourceBundle messages;

try {
    messages = ResourceBundle.getBundle("ru.ifmo.eshop.i18n.MessagesBundle", locale);
} catch (MissingResourceException e) {
    locale = new Locale("en", "US");
    messages = ResourceBundle.getBundle("ru.ifmo.eshop.i18n.MessagesBundle", locale);
}
pageContext.setAttribute("resourceBundle", messages,PageContext.REQUEST_SCOPE);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Eshop</title>
    <link type="text/css" rel="stylesheet" href="/static/main.css">
    <link type="application/rss+xml" rel="alternate" title="RSS" href="/rss">
    <script type="text/javascript">
	function validateEmail(elementValue){
		var emailPattern = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		if (!emailPattern.test(elementValue)) {
			window.alert('incorrect email');
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
<body>
  <form action="http://www.google.com/cse" id="cse-search-box">
    <div style="float:left;text-align:left">
      <input name="cx" value="003399418647576449102:egk553zukni" type="hidden">
      <input name="ie" value="UTF-8" type="hidden">
      <input class="gwt-TextBox" name="q" size="31" type="text">
      <input src="/static/images/find.png" name="sa" title="Search" value="Search" type="image">
    </div>
    <a class="topMenuLink" href="/subscribe" title="<bundle:message key="user.subscribe"/>" onclick="showSubscribe();return false;">
        <img src="/static/images/email.png"> <bundle:message key="user.subscription"/></a>
    <a class="topMenuLink" href="#"><img src="/static/images/t_mini-b.png"> Eshop в Twitter</a>

    <a class="topMenuLink" href="register.html" onclick="showLogin();return false;">
        <img src="/static/images/user.png"> <bundle:message key="user.signup"/>
    </a>
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
	<input value="Subscribe" onclick="return validateEmail(document.getElementById('email').value)" type="submit">
</form>
<div id="leftMenu">
	<ul class="leftMenu">
		<li class="menuHeader"><bundle:message key="user.menu"/></li>
		<li class="leftMenu">
                    <a href="/" class="menuLink">
                        <bundle:message key="user.main"/>
                    </a>
                </li>
		<li class="leftMenu">
                    <a href="artists.html" class="menuLink">
                        <bundle:message key="user.artists"/>
                    </a>
                </li>
		<li class="leftMenu">
                    <a href="cart.html" class="menuLink">
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
    <b><bundle:message key="user.items.last"/></b><br /><br />
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
