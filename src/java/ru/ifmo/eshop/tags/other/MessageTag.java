package ru.ifmo.eshop.tags.other;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author alex
 * 23.05.2011
 */
public class MessageTag extends TagSupport {
    private ResourceBundle src;
    private String key;

    public ResourceBundle getSrc() {
        return src;
    }

    public void setSrc(ResourceBundle src) {
        this.src=src;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key=key;
    }

    @Override
    public int doStartTag() throws JspException {
        if (src==null) {
            src=(ResourceBundle)pageContext.getAttribute("resourceBundle",PageContext.REQUEST_SCOPE);
        }
        try {
            pageContext.getOut().print(src.getString(key));
        } catch (IOException ex) {
            throw new JspException(ex);
        }
        src=null;
        return SKIP_BODY;
    }

    @Override
    public void release() {
        key=null;
        src=null;
    }
}
