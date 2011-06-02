package ru.ifmo.eshop.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.ifmo.eshop.Eshop;
import ru.ifmo.eshop.storage.Delivery;
import ru.ifmo.eshop.storage.DeliveryItem;
import ru.ifmo.eshop.storage.Distributor;
import ru.ifmo.eshop.storage.Item;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public class DeliveryServlet extends HttpServlet {
   
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
        PrintWriter writer=response.getWriter();
        int[] ids=null;
        int id=0;
        Delivery delivery=null;
        if (!delete) {
            Integer distributorId=null;
            String temp=request.getParameter("distributorId");
            try {
                distributorId=Integer.valueOf(temp);
                if (!add) id=Integer.valueOf(request.getParameter("id"));
            } catch(NumberFormatException e) {
                error=true;
                writer.println("wrong id or distributor id");
            }
            String orderDate=request.getParameter("orderDate");
            if (orderDate==null || orderDate.isEmpty()) {
                error=true;
                writer.println("order date have to be set");
            }
            String deliverDate=request.getParameter("deliverDate");
            if (deliverDate==null || deliverDate.isEmpty()) {
                error=true;
                writer.println("deliver date have to be set");
            }
            try {
                if (add) {
                    delivery=StorageManager.registerDelivery(distributorId,
                            Date.valueOf(orderDate), Date.valueOf(deliverDate));
                } else {
                    delivery=new Delivery(id,
                            new Distributor(distributorId, "d_type", "title",
                            "country"),Date.valueOf(orderDate),
                            Date.valueOf(deliverDate));
                }
            } catch (IllegalArgumentException e) {
                error=true;
                writer.println(e.getMessage());
            }
            if (error) {
                //response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            temp=request.getParameter("items");
            ArrayList<DeliveryItem> items=new ArrayList<DeliveryItem>();
            if (temp==null || temp.isEmpty()) {
                error=true;
                writer.println("Items have to be set");
            } else {
                for (String s:temp.split(",")) {
                    String[] params=s.split(";");
                    if (params.length!=3) {
                        error=true;
                        writer.println("wrong item parameters: "+s);
                        break;
                    }
                    try {
                        DeliveryItem di=new DeliveryItem(
                                new Item(
                                Integer.valueOf(params[0]),
                                "mediaType","format","title",
                                delivery.getOrderDate()),
                                Integer.parseInt(params[1]),
                                Double.parseDouble(params[2]));
                       items.add(di);
                    } catch(NumberFormatException e) {
                        error=true;
                        writer.println(e.getMessage());
                        break;
                    }
                }
                if (items.size()==0) {
                    error=true;
                    writer.println("Items have to be set");
                } else {
                    delivery.setItems(items);
                }
            }
            if (error) {
                //response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } else {
            String deliveries=request.getParameter("ids");
            if (deliveries==null || deliveries.isEmpty()) {
                error=true;
            } else {
                int i=0;
                String as[]=deliveries.split(",");
                ids=new int[as.length];
                for (String a:as) {
                    try {
                        ids[i++]=Integer.parseInt(a);
                    } catch(NumberFormatException e) {
                        writer.println("One of the ids is incorrect");
                        error=true;
                        break;
                    }
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
                    sm.deleteDelivery(i);
                }
            } else if (add) {
                sm.addDelivery(delivery);
            } else {
                sm.updateDelivery(delivery);
            }
            sm.close();
            response.sendRedirect("/admin/deliveries.jsp");
        } catch(Exception e) {
            e.printStackTrace();
            Cookie c=new Cookie("errorCode", "1");
            c.setPath("/admin");
            response.addCookie(c);
            if (delete) {
                c=new Cookie("return","/admin/deliveries.jsp");
            } else if (add) {
                c=new Cookie("return","/admin/deliveries.jsp?act=add");
            } else {
                c=new Cookie("return","/admin/deliveries.jsp?act=edit&id="+id);
            }
            c.setPath("/admin");
            response.addCookie(c);
            response.sendRedirect("/admin/error.jsp");
        }
    }
}
