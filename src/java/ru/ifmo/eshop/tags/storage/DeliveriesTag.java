package ru.ifmo.eshop.tags.storage;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Delivery;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 * 03.06.2011
 */
public class DeliveriesTag extends RecordsTag {

    private List<Delivery> deliveries;
    private int index=0;

    public Delivery fetchDelivery() {
        return deliveries.get(index++);
    }

    @Override
    public int doStartTag() throws JspException {
        StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
        try {
            deliveries=sm.getDeliveries(start, end);
        } catch (SQLException ex) {
            throw new JspException(ex);
        }
        index=0;
        if (deliveries.isEmpty()) {
            return SKIP_BODY;
        } else {
            return EVAL_BODY_INCLUDE;
        }
    }

    @Override
    public int doAfterBody() throws JspException {
        if (index<deliveries.size()) {
            return EVAL_BODY_AGAIN;
        } else {
            return EVAL_PAGE;
        }
    }

    @Override
    public void release() {
        super.release();
        deliveries=null;
    }
}
