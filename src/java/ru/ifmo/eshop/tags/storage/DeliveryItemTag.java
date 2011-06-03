package ru.ifmo.eshop.tags.storage;

import javax.servlet.jsp.JspException;
import ru.ifmo.eshop.storage.DeliveryItem;

/**
 *
 * @author alex
 * 31.05.2011
 */
public class DeliveryItemTag extends RecordTag {

    @Override
    public int doStartTag() throws JspException {
        DeliveryListTag deliveryListTag=(DeliveryListTag) RecordTag.getAncestor(this,
                "ru.ifmo.eshop.tags.storage.DeliveryListTag");
        if (deliveryListTag==null) {
            throw new JspException("No parent DeliveryListTag found");
        }
        DeliveryItem d=(DeliveryItem)deliveryListTag.getItem();
        fieldMap.put("item.title",d.getItem().getTitle());
        fieldMap.put("amount",d.getAmount());
        fieldMap.put("price",d.getPrice());
        fieldMap.put("item.id",d.getItem().getId());
        return EVAL_BODY_INCLUDE;
    }
}
