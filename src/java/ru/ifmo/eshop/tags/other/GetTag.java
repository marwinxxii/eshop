package ru.ifmo.eshop.tags.other;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author alex
 * 16.05.2011
 */
public class GetTag extends TagSupport {

    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field=field;
    }

    @Override
    public int doStartTag() throws JspException {
        String value=pageContext.getRequest().getParameter(field);
        try {
            if (value==null) {
                pageContext.getOut().print("");
            } else {
                pageContext.getOut().print(value);
            }
        } catch (IOException ex) {
            throw new JspException(ex);
        }
        return SKIP_BODY;
    }
}
