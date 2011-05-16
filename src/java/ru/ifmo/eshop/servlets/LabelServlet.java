/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.ifmo.eshop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
        boolean delete=false;
        if (act!=null && act.equals("save")) {
            add=false;
        } else if (act!=null && act.equals("del")) {
            delete=true;
            add=false;
        }
        boolean error=false;
        String title=null;
        String country=null;
        PrintWriter writer=response.getWriter();
        int ids[]=null;
        if (!delete) {
            title=request.getParameter("title");
            country=request.getParameter("country");
            if (title==null || title.isEmpty()) {
                error=true;
                writer.println("Title have to be set");
            } else if (title.length()>Label.TITLE_LENGTH) {
                error=true;
                writer.println("Title is too long");
            }
            if (country!=null && country.length()>Label.COUNTRY_LENGTH) {
                error=true;
                writer.println("Country is too long");
            }
            /*if (error) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }*/
        } else {
            String genres=request.getParameter("ids");
            if(genres==null || genres.isEmpty()) {
                error=true;
            } else {
                int i=0;
                String gs[]=genres.split(",");
                ids=new int[gs.length];
                for (String g:gs) {
                    ids[i++]=Integer.parseInt(g);
                }
            }
        }
        int id=0;
        if (!add && !delete) {
            String  sid=request.getParameter("id");
            if (sid==null || sid.isEmpty()) {
                error=true;
                writer.println("ID is null");
            } else {
                try {
                    id=Integer.parseInt(sid);
                    if (id<=0) error=true;
                    writer.println("Wrong id");
                } catch (NumberFormatException e) {
                    error=true;
                }
            }
        }
        if (error) {
            //response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            StorageManager sm = Eshop.getStorageManager();
            if (add) {
                sm.addLabel(title,country);
            } else if (!delete) {
                sm.updateLabel(id, title, country);
            } else {
                //TODO optimization
                for (int i:ids) {
                    sm.deleteLabel(i);
                }
            }
            sm.close();
            response.sendRedirect("/admin/labels.jsp");
        //} catch (ClassNotFoundException ex) {
        } catch (Exception ex) {
            //TODO logging and exceptions
            ex.printStackTrace();
            //response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
            Cookie c=new Cookie("errorCode", "1");
            c.setPath("/admin");
            response.addCookie(c);
            c=new Cookie("return","/admin/labels.jsp?act="+act+"&id="+id);
            c.setPath("/admin");
            response.addCookie(c);
            response.sendRedirect("/admin/error.jsp");
        }/* catch (SQLException ex) {
            ex.printStackTrace();
            //response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
        }*/
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
