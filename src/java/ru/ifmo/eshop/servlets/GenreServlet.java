/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.ifmo.eshop.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.ifmo.eshop.Eshop;
import ru.ifmo.eshop.storage.Genre;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public class GenreServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        //TODO check user role
        String act=request.getParameter("act");
        boolean add=true;
        if (act!=null && act.equals("save")) {
            add=false;
        }
        boolean error=false;
        String mes="";
        String title=request.getParameter("title");
        String description=request.getParameter("description");
        if (title==null || title.isEmpty()) {
            error=true;
            mes="title null";
        } else if (title.length()>Genre.TITLE_LENGTH) {
            error=true;
            mes="title long";
        }
        if (description==null || description.isEmpty()) {
            error=true;
            mes="desc null";
        } else if (description.length()>Genre.DESCRIPTION_LENGTH) {
            error=true;
            mes="desc long";
            mes+=description;
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
            response.getWriter().println(mes);
            return;
        }
        try {
            StorageManager sm = Eshop.getStorageManager();
            if (add) {
                sm.addGenre(title,description);
            } else {
                sm.updateGenre(id, title, description);
            }
            sm.close();
            //TODO redirect
            response.sendRedirect("/admin/");
        } catch (ClassNotFoundException ex) {
            //TODO logging and exceptions
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
            Logger.getLogger(GenreServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GenreServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for adding/editing genres";
    }// </editor-fold>

}
