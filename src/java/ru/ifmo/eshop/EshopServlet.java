package ru.ifmo.eshop;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.*;

import ru.ifmo.eshop.storage.Item;
import ru.ifmo.eshop.storage.StorageManager;

public class EshopServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("item:");
        try {
            StorageManager sm = new StorageManager("benderhost", 1521, "eshop", "eshop");
            Item i = sm.getItem(1);
            resp.getWriter().println(i.getTitle());
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(resp.getWriter());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(resp.getWriter());
        }
    }
}
