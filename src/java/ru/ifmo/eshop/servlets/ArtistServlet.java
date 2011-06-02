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
import ru.ifmo.eshop.storage.Artist;
import ru.ifmo.eshop.storage.Genre;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public class ArtistServlet extends HttpServlet {

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
        Artist artist=null;
        PrintWriter writer=response.getWriter();
        int[] ids=null;
        int id=0;
        if (!delete) {
            String temp;
            String title=null;
            int genreId=0;
            title=request.getParameter("title");
            if (!add) {
                temp=request.getParameter("id");
                try {
                    if (temp==null || temp.isEmpty()) throw new NumberFormatException();
                    id=Integer.parseInt(temp);
                } catch(NumberFormatException e) {
                    error=true;
                    writer.println("Wrong id of artist");
                }
            }
            temp=request.getParameter("genreId");
            if (temp==null || temp.isEmpty()) {
                error=true;
                writer.println("Genre ID have to be set");
            } else {
                try {
                    genreId=Integer.parseInt(temp);
                    if (add) {
                        artist=StorageManager.registerArtist(title, genreId);
                    } else {
                        artist=new Artist(id, title, Genre.registerGenre(id));
                    }
                } catch (NumberFormatException e) {
                    error=true;
                    writer.println("Wrong genre ID");
                } catch(IllegalArgumentException e) {
                    writer.println("Incorrect title");
                    error=true;
                }
            }
            if (error) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Integer year;
            temp=request.getParameter("beginYear");
            if (temp!=null && !temp.isEmpty()) {
                try {
                    year=Integer.valueOf(temp);
                    artist.setBeginYear(year);
                } catch (Exception e) {
                    error=true;
                    writer.println("Incorrect begin year");
                }
            }
            temp=request.getParameter("endYear");
            if (temp!=null && !temp.isEmpty()) {
                try {
                    year=Integer.valueOf(temp);
                    artist.setEndYear(year);
                } catch (Exception e) {
                    error=true;
                    writer.println("Incorrect end year");
                }
            }
            temp=request.getParameter("country");
            if (temp!=null && !temp.isEmpty()) {
                try {
                    artist.setCountry(temp);
                } catch(IllegalArgumentException e) {
                    error=true;
                    writer.println("Incorrect country");
                }
            }
        } else {
            String artists=request.getParameter("ids");
            if (artists==null || artists.isEmpty()) {
                error=true;
            } else {
                int i=0;
                String as[]=artists.split(",");
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
                    sm.deleteArtist(i,false);
                }
            } else if (add) {
                sm.addArtist(artist);
            } else {
                sm.updateArtist(artist);
            }
            sm.close();
            response.sendRedirect("/admin/artists.jsp");
        } catch(Exception e) {
            e.printStackTrace();
            Cookie c=new Cookie("errorCode", "1");
            c.setPath("/admin");
            response.addCookie(c);
            if (delete) {
                c=new Cookie("return","/admin/artists.jsp");
            } else if (add) {
                c=new Cookie("return","/admin/artists.jsp?act=add");
            } else {
                c=new Cookie("return","/admin/artists.jsp?act=edit&id="+id);
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
        return "Servlet for adding/editing artists";
    }

}
