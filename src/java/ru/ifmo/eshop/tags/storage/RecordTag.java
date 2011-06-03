package ru.ifmo.eshop.tags.storage;

import java.util.HashMap;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Class-ancestor for all tags that describe database entitites.
 * Used to get values of object's fields.
 * @author alex
 */
public abstract class RecordTag extends TagSupport {

    /**
     * Primary key of record in database.
     */
    protected int keyId=-1;
    /**
     * Message that will be printed if requsted field value is null.
     */
    protected String message;
    protected HashMap<String,Object> fieldMap=new HashMap<String, Object>();
    //TODO may be I should add method getFieldsList?

    /**
     * Get value of the specified field.
     * @param fieldName
     * @return Object value of field. Throws IllegalArgument Exception if
     * there is no such field in object.
     */
    public Object getFieldValue(String fieldName) {
        if (fieldMap.containsKey(fieldName)) {
            return fieldMap.get(fieldName);
        } else {
            throw new IllegalArgumentException("Wrong fieldName: "+fieldName);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int id) {
        if (id<=0) throw new IllegalArgumentException("id is <=0");
        this.keyId=id;
    }

    /**
     * Get ancestor tag of the specified class.
     * @param tag child-tag
     * @param className name of parent tag
     * @return parent-tag if found
     * @throws JspException if parent tag wasn't found
     */
    public static TagSupport getAncestor(Tag tag,String className)
            throws JspException {
        Class klass = null;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new JspException(ex);
        }
        return (TagSupport) findAncestorWithClass(tag, klass);
    }

    @Override
    public void release() {
        message = null;
    }
}
