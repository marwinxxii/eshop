package ru.ifmo.eshop.storage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.ifmo.eshop.Eshop;

/**
 *
 * @author alex
 */
public class SignupServlet extends HttpServlet {

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        String email=request.getParameter("email");
        String info=request.getParameter("info");
        String password=request.getParameter("password");
        String address=request.getParameter("address");
        String phone=request.getParameter("phone");
        PrintWriter writer=response.getWriter();
        boolean error=false;
        if (email==null || email.isEmpty()) {
            error=true;
            writer.println("email have to be set");
        } else if (email.length()>Customer.EMAIL_LENGTH) {
            error=true;
            writer.println("email is too long");
        }
        if (info==null || info.isEmpty()) {
            error=true;
            writer.println("info have to be set");
        } else if (info.length()>Customer.INFO_LENGTH) {
            error=true;
            writer.println("info is too long");
        }
        if (password==null || password.isEmpty()) {
            error=true;
            writer.println("password have to be set");
        } else if (password.length()>Customer.PASSWORD_LENGTH) {
            error=true;
            writer.println("password is too long");
        }
        if (address==null || address.isEmpty()) {
            error=true;
            writer.println("address have to be set");
        } else if (address.length()>Customer.ADDRESS_LENGTH) {
            error=true;
            writer.println("address is too long");
        }
        if (error) {
            return;
        }
        try {
            StorageManager sm = Eshop.getStorageManager();
            sm.addCustomer(email, password, address,phone);
            sm.close();
            response.addCookie(new Cookie("signedIn","true"));
            response.sendRedirect("/");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/signup.jsp");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
