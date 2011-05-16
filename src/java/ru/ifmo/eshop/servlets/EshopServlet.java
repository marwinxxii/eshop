package ru.ifmo.eshop.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.*;
import ru.ifmo.eshop.Eshop;

import ru.ifmo.eshop.storage.Item;
import ru.ifmo.eshop.storage.StorageManager;

public class EshopServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("item:");
        try {
            StorageManager sm = Eshop.getStorageManager();
            sm.addGenre("Heavy Metal", "Тяжёлый метал, прародитель всех остальных веток метала. Сочетает в себе мощные гитарные рифы и соло энергичный вокал с сильными текстами.");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(resp.getWriter());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(resp.getWriter());
        }
    }
}
