package ru.ifmo.eshop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.ifmo.eshop.Eshop;
import ru.ifmo.eshop.storage.Item;
import ru.ifmo.eshop.storage.Label;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public class ItemServlet extends HttpServlet {

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
        Item item=null;
        PrintWriter writer=response.getWriter();
        int[] ids=null;
        int id=0;
        if (!delete) {
            String temp;
            String title=null;
            Integer labelId=null;
            title=request.getParameter("title");
            if (!add) {
                temp=request.getParameter("id");
                try {
                    id=Integer.parseInt(temp);
                } catch(NumberFormatException e) {
                    error=true;
                    writer.println("Wrong id of item");
                }
            }
            temp=request.getParameter("labelId");
            if (temp!=null && !temp.isEmpty()) {
                try {
                    labelId=Integer.valueOf(temp);
                } catch (NumberFormatException e) {
                    error=true;
                    writer.println("Wrong label ID");
                }
            }
            String mediaType=request.getParameter("mediaType");
            String format=request.getParameter("format");
            temp=request.getParameter("releaseDate");
            if (temp==null || temp.isEmpty()) {
                error=true;
                writer.println("Release date have to be set");
            }
            if (error) {
                //response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            try {
                if (add) {
                    item=StorageManager.registerItem(mediaType, format, title, Date.valueOf(temp));
                } else {
                    item=new Item(id,mediaType, format, title, Date.valueOf(temp));
                }
                temp=request.getParameter("cover");
                if (temp!=null && !temp.isEmpty()) {
                    item.setCover(temp);
                }
                if (labelId!=null) {
                    item.setLabel(new Label(labelId,"label"));
                }
            } catch(IllegalArgumentException e) {
                error=true;
                writer.println(e.getMessage());
            }
        } else {
            String items=request.getParameter("ids");
            if (items==null || items.isEmpty()) {
                error=true;
            } else {
                int i=0;
                String as[]=items.split(",");
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
                    sm.deleteItem(i);
                }
            } else if (add) {
                sm.addItem(item);
            } else {
                sm.updateItem(item);
            }
            sm.close();
            response.sendRedirect("/admin/items.jsp");
        } catch(Exception e) {
            e.printStackTrace();
            Cookie c=new Cookie("errorCode", "1");
            c.setPath("/admin");
            response.addCookie(c);
            if (delete) {
                c=new Cookie("return","/admin/items.jsp");
            } else if (add) {
                c=new Cookie("return","/admin/items.jsp?act=add");
            } else {
                c=new Cookie("return","/admin/items.jsp?act=edit&id="+id);
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
        return "Servlet for adding/editing items";
    }

}
