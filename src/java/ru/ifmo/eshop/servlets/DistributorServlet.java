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
import ru.ifmo.eshop.storage.Distributor;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public class DistributorServlet extends HttpServlet {

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
        Distributor distributor=null;
        PrintWriter writer=response.getWriter();
        int[] ids=null;
        int id=0;
        if (!delete) {
            String temp;
            if (!add) {
                temp=request.getParameter("id");
                try {
                    id=Integer.parseInt(temp);
                } catch(NumberFormatException e) {
                    error=true;
                    writer.println("Wrong id of item");
                }
            }
            String title=request.getParameter("title");
            String type=request.getParameter("type");
            String country=request.getParameter("country");
            try {
                if (add) {
                    distributor=StorageManager.registerDistributor(type, title, country);
                } else {
                    distributor=new Distributor(id, type, title, country);
                }
            } catch(IllegalArgumentException e) {
                error=true;
                writer.println(e.getMessage());
            }
        } else {
            String tracks=request.getParameter("ids");
            if (tracks==null || tracks.isEmpty()) {
                error=true;
            } else {
                int i=0;
                String as[]=tracks.split(",");
                ids=new int[as.length];
                for (String a:as) {
                    ids[i++]=Integer.parseInt(a);
                }
            }
        }
        if (error) {
            //response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            StorageManager sm=Eshop.getStorageManager();
            if (delete) {
                //TODO optimize
                for (int i:ids) {
                    sm.deleteDistributor(i);
                }
            } else if (add) {
                sm.addDistributor(distributor);
            } else {
                sm.updateDistributor(distributor);
            }
            sm.close();
            response.sendRedirect("/admin/distributors.jsp");
        } catch(Exception e) {
            e.printStackTrace();
            Cookie c=new Cookie("errorCode", "1");
            c.setPath("/admin");
            response.addCookie(c);
            if (delete) {
                c=new Cookie("return","/admin/distributors.jsp");
            } else if (add) {
                c=new Cookie("return","/admin/distributors.jsp?act=add");
            } else {
                c=new Cookie("return","/admin/distributors.jsp?act=edit&id="+id);
            }
            c.setPath("/admin");
            response.addCookie(c);
            response.sendRedirect("/admin/error.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for adding/editing tracks";
    }

}
