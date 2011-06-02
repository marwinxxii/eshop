package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import ru.ifmo.eshop.storage.Delivery;

/**
 *
 * @author alex
 * 31.05.2011
 */
public class DeliveryTag extends EntityTag {

    @Override
    public int doStartTag() throws JspException {
        RecordTag recordTag=(RecordTag) RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.RecordTag");
        if (recordTag==null) {
            throw new JspException("No parent RecordTag found");
        }
        Delivery d=(Delivery)recordTag.getRecord();
        String content;
        //TODO validation of attribute values?
        if (field.equals("id")) {
            content=String.valueOf(d.getId());
        } else if (field.equals("distributor.title")) {
            content=d.getDistributor().getTitle();
        } else if (field.equals("distributor.id")) {
            content=String.valueOf(d.getDistributor().getId());
        } else if (field.equals("deliverDate")) {//TODO rename
            content=String.valueOf(d.getDeliverDate());
        } else if (field.equals("size")) {
            content=String.valueOf(d.getSize());
        } else if (field.equals("orderDate")) {
            content=String.valueOf(d.getOrderDate());
        } else {
            throw new JspException("DeliveryTag:Wrong field name");
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
