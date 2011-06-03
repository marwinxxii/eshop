package ru.ifmo.eshop.tags.storage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import ru.ifmo.eshop.Eshop;

/**
 * Tag that initialises StorageManager for current page request and stores it
 * in page context.
 * @author alex
 * 02.06.2011
 */
public class ManagerTag extends TagSupport {

    /**
     * Key for StorageManager attribute in page context.
     */
    private String key="storageManager";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        if (key==null || key.isEmpty()) {
            throw new IllegalArgumentException("Key is null or empty");
        }
        this.key = key;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.setAttribute(key, Eshop.getStorageManager(),PageContext.REQUEST_SCOPE);
            return SKIP_BODY;
        } catch (Exception ex) {
            throw new JspException(ex);
        }
    }
}
