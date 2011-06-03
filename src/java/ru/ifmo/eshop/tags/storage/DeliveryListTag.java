package ru.ifmo.eshop.tags.storage;

import java.util.List;
import javax.servlet.jsp.JspException;
import ru.ifmo.eshop.storage.DeliveryItem;
import ru.ifmo.eshop.storage.Delivery;

/**
 *
 * @author alex
 * 31.05.2011
 */
public class DeliveryListTag extends RecordTag {
    private int index;
    private List<DeliveryItem> items;

    @Override
    public int doStartTag() throws JspException {
        DeliveryTag deliveryTag=(DeliveryTag) RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.DeliveryTag");
        if (deliveryTag==null) {
            throw new JspException("No parent DeliveryTag found");
        }
        items=deliveryTag.getDelivery().getItems();
        index=0;
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {
        index++;
        if (index<items.size()) {
            return EVAL_BODY_AGAIN;
        }
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        items=null;
    }

    public DeliveryItem getItem() {
        return items.get(index);
    }
}
