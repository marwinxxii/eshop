package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import ru.ifmo.eshop.storage.DeliveryItem;

/**
 *
 * @author alex
 * 31.05.2011
 */
public class DeliveryItemTag extends EntityTag {

    @Override
    public int doStartTag() throws JspException {
        DeliveryListTag deliveryListTag=(DeliveryListTag) RecordTag.getAncestor(this,
                "ru.ifmo.eshop.tags.storage.DeliveryListTag");
        if (deliveryListTag==null) {
            throw new JspException("No parent DeliveryListTag found");
        }
        DeliveryItem d=(DeliveryItem)deliveryListTag.getItem();
        String content;
        //TODO validation of attribute values?
        if (field.equals("title")) {
            content=d.getItem().getTitle();
        } else if (field.equals("amount")) {
            content=String.valueOf(d.getAmount());
        } else if (field.equals("price")) {//TODO rename
            content=String.valueOf(d.getPrice());
        } else if (field.equals("item.id")) {
            content=String.valueOf(d.getItem().getId());
        } else {
            throw new JspException("DeliveryItemTag:Wrong field name");
        }
        /*if (content.length()>length) {
            content=content.substring(0,length)+"...";
        }*/
        try {
            pageContext.getOut().print(content);
            return SKIP_BODY;
        } catch (IOException ex) {
            throw new JspException(ex);
        }
    }
}
