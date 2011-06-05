<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/tlds/i18n.tld" prefix="bundle" %>
<script type="text/javascript">
    document.getElementById('signup').onsubmit=function(event) {
        alert('shit');
        if (!validateEmail(document.getElementById('email').value)) {
            event.preventDefault();
        }
    }
</script>
<h2><bundle:message key="user.forms.signup"/></h2>
<form action="/signup" method="post" id="signup">
<bundle:message key="user.forms.signup.email"/><span class="red">*</span>:
<input type="textbox" name="email" id="email"><br>
<bundle:message key="user.forms.signup.password"/><span class="red">*</span>:
<input type="password" name="password"><br>
<bundle:message key="user.forms.signup.address"/><span class="red">*</span>:
<input type="textbox" name="address" id="email"><br>
<bundle:message key="user.forms.signup.phone"/><span class="red">*</span>:
<input type="textbox" name="phone" id="phone"><br>
<bundle:message key="forms.mandatory"/><br>
<input type="submit" value="<bundle:message key="user.forms.signup.do"/>">
</form>