package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import ru.ifmo.eshop.storage.Label;

/**
 *
 * @author alex
 * 18.04.2011
 */
public class LabelTag extends EntityTag {

    @Override
    public int doStartTag() throws JspException {
        RecordTag recordTag=(RecordTag) RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.RecordTag");
        if (recordTag==null) {
            throw new JspException("No parent RecordTag found");
        }
        Label l=(Label)recordTag.getRecord();
        String content;
        //TODO validation of attribute values?
        if (field.equals("id")) {
            content=String.valueOf(l.getId());
        } else if (field.equals("title")) {
            content=l.getTitle();
        } else if (field.equals("country")) {
            content=l.getCountry();
        } else {
            throw new JspException("LabelTag:Wrong field name");
        }
        if (content.length()>length) {
            content=content.substring(0,length)+"...";
        }
        try {
            pageContext.getOut().print(content);
            return SKIP_BODY;
        } catch (IOException ex) {
            throw new JspException(ex);
        }
    }

    @Override
    public void release() {
        field = null;
    }
}
