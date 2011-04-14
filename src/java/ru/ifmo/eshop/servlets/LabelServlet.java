/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.ifmo.eshop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.ifmo.eshop.Eshop;
import ru.ifmo.eshop.storage.Label;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public class LabelServlet extends HttpServlet {

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
        request.setCharacterEncoding("utf-8");
        //TODO check user role
        String act=request.getParameter("act");
        boolean add=true;
        if (act!=null && act.equals("save")) {
            add=false;
        }
        boolean error=false;
        String mes="";
        String title=request.getParameter("title");
        String country=request.getParameter("country");
        if (title==null || title.isEmpty()) {
            error=true;
            mes="title null";
        } else if (title.length()>Label.TITLE_LENGTH) {
            error=true;
            mes="title long";
        }
        if (country!=null && country.length()>Label.COUNTRY_LENGTH) {
            error=true;
            mes="country long";
            mes+=country;
        }
        /*if (error) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }*/
        int id=0;
        if (!add) {
            String  sid=request.getParameter("id");
            if (sid==null || sid.isEmpty()) {
                error=true;
                mes="id null";
            } else {
                try {
                    id=Integer.valueOf(sid);
                    if (id<=0) error=true;
                    mes="id<=0";
                } catch (NumberFormatException e) {
                    error=true;
                    mes="numbfexc";
                }
            }
        }
        if (error) {
            //response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            //TODO remove later debug info
            response.getWriter().println(mes);
            return;
        }
        try {
            StorageManager sm = Eshop.getStorageManager();
            if (add) {
                sm.addLabel(title,country);
            } else {
                sm.updateLabel(id, title, country);
            }
            sm.close();
            response.sendRedirect("/admin/labels.jsp");
        } catch (ClassNotFoundException ex) {
            //TODO logging and exceptions
            ex.printStackTrace(response.getWriter());
            //response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
        } catch (SQLException ex) {
            ex.printStackTrace(response.getWriter());
            //response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for adding/editing labels";
    }

}
