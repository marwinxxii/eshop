package ru.ifmo.eshop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alex
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String login=request.getParameter("login");
        String password=request.getParameter("password");
        if (login==null || password==null) {
            System.out.println("login or password is null");
            response.sendRedirect("/admin/login.jsp");
            return;
        }
        if (!login.equals("admin") || !password.equals("password")) {
            System.out.println("login or password is wrong");
            response.sendRedirect("/admin/login.jsp");
            return;
        }
        String ret=request.getParameter("return");
        if (ret==null || ret.isEmpty()) {
            ret="/admin";
        }
        Cookie c=new Cookie("loggedIn","true");
        c.setPath("/admin");
        //c.setMaxAge(7200);
        response.addCookie(c);
        response.sendRedirect(ret);
    }
}
