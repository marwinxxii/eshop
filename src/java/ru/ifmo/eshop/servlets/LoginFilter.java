package ru.ifmo.eshop.servlets;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alex
 * 04.06.2011
 */
public class LoginFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws ServletException,IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        if (httpReq.getRequestURI().equals("/admin/login.jsp")
                || httpReq.getRequestURI().equals("/admin/login")) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletResponse httpResp=(HttpServletResponse) response;
        Cookie[] cookies=httpReq.getCookies();
        if (cookies==null) {
            Cookie c=new Cookie("return",httpReq.getRequestURI());
            c.setPath("/admin/login.jsp");
            c.setMaxAge(120);
            httpResp.addCookie(c);
            httpResp.sendRedirect("/admin/login.jsp");
            //httpResp.getWriter().println("shit");
            //filterConfig.getServletContext().getRequestDispatcher("/admin/login.jsp").forward(request, response);
        } else {
            boolean loggedIn=false;
            for (Cookie c:cookies) {
                if (c.getName().equals("loggedIn")) {
                    if (c.getValue().equals("true")) {
                        loggedIn=true;
                    } else if (c.getValue().equals("false")) {
                        loggedIn=false;
                    }
                    //break;
                }
            }
            if (!loggedIn) {
                Cookie c=new Cookie("return",httpReq.getRequestURI());
                c.setPath("/admin/login.jsp");
                c.setMaxAge(60);
                httpResp.addCookie(c);
                //httpResp.getWriter().println("shit");
                httpResp.sendRedirect("/admin/login.jsp");
                //filterConfig.getServletContext().getRequestDispatcher("/admin/login.jsp").forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig=filterConfig;
    }
}
