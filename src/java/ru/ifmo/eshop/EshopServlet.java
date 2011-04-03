package ru.ifmo.eshop;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.*;

import ru.ifmo.eshop.storage.Item;
import ru.ifmo.eshop.storage.StorageManager;

@SuppressWarnings("serial")
public class EshopServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		try {
			StorageManager sm=new StorageManager("benderhost", 1521, "eshop", "esop");
			Item i=sm.getItem(1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
