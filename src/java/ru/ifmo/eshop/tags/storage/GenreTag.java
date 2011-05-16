package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import ru.ifmo.eshop.storage.Genre;

/**
 *
 * @author alex
 */
public class GenreTag extends EntityTag {

    @Override
    public int doStartTag() throws JspException {
        RecordTag recordTag=(RecordTag) RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.RecordTag");
        if (recordTag==null) {
            throw new JspException("No parent RecordTag found");
        }
        Genre g=(Genre)recordTag.getRecord();
        String content;
        //TODO validation of attribute values?
        if (field.equals("id")) {
            content=String.valueOf(g.getId());
        } else if (field.equals("title")) {
            content=g.getTitle();
        } else if (field.equals("description")) {
            content=g.getDescription();
        } else {
            throw new JspException("GenreTag:Wrong field name");
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
