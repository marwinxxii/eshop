package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Class for accessing object's fields values through jsp tags.
 * @author alex
 * 02.06.2011
 */
public class FieldTag extends TagSupport {

    /**
     * Name of the field to get from object.
     */
    private String name;

    /**
     * Max length of field value. If length of value is more than this, it
     * will be truncated and added '...' to the end.
     */
    private int length=1000;

    /**
     * Message that will be displayed if requested field value is null.
     */
    private String message="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name==null || name.isEmpty())
            throw new IllegalArgumentException("Field is null or empty");
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        if (length<=0) throw new IllegalArgumentException("length is <=0");
        this.length = length;
    }

    @Override
    public int doStartTag() throws JspException {
        RecordTag tag=(RecordTag)RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.RecordTag");
        if (tag==null) {
            throw new JspException("Parent RecordTag not found");
        }
        String content;
        Object o=tag.getFieldValue(name);
        if (o==null) {
            content=message;
        } else {
            content=String.valueOf(o);
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
}
