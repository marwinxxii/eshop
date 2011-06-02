package ru.ifmo.eshop.tags.storage;

import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author alex
 * 16.05.2011
 */
public class EntityTag extends TagSupport {

    protected String field;
    protected int length=1000;
    protected String defaultValue="";

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String value) {
        if (value==null || defaultValue.length()>length)
            throw new IllegalArgumentException("Default value is null or too long");
        defaultValue=value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        if (field==null || field.isEmpty()) {
            throw new IllegalArgumentException("Field name is null or empty");
        }
        this.field=field;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        if (length<=0) {
            throw new IllegalArgumentException("Length is <=0");
        }
        this.length=length;
    }

    @Override
    public void release() {
        field=null;
        defaultValue=null;
    }
}
