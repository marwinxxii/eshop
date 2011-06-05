package ru.ifmo.eshop.tags.other;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author alex
 * 05.06.2011
 */
public class GetBundleTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
        HttpServletResponse response=(HttpServletResponse)pageContext.getResponse();
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
        return SKIP_BODY;
    }
}
