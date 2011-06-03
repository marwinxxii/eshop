package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Delivery;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 * 31.05.2011
 */
public class DeliveryTag extends RecordTag {

    private Delivery delivery;

    public Delivery getDelivery() {
        return delivery;
    }

    @Override
    public int doStartTag() throws JspException {
        if (keyId==-1) {
            DeliveriesTag tag=(DeliveriesTag)RecordTag.getAncestor(this,
                    "ru.ifmo.eshop.tags.storage.DeliveriesTag");
            if (tag==null) {
                throw new JspException("No parent DeliveriesTag found");
            }
            delivery=tag.fetchDelivery();
        } else {
            StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
            try {
                delivery = sm.getDelivery(keyId);
            } catch (SQLException ex) {
                throw new JspException(ex);
            }
        }
        if (delivery==null) {
            try {
                pageContext.getOut().print(message);
                return SKIP_BODY;
            } catch (IOException ex) {
                throw new JspException(ex);
            }
        }
        fieldMap.put("id", delivery.getId());
        fieldMap.put("orderDate",delivery.getOrderDate());
        fieldMap.put("deliverDate", delivery.getDeliverDate());
        fieldMap.put("distributor.id",delivery.getDistributor().getId());
        fieldMap.put("distributor.title",delivery.getDistributor().getTitle());
        fieldMap.put("size", delivery.getSize());
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public void release() {
        super.release();
        delivery=null;
    }
}
