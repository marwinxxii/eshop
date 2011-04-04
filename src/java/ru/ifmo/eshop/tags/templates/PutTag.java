/**
 *
 * @author alex
 */
package ru.ifmo.eshop.tags.templates;

import java.util.Hashtable;
import java.util.Stack;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import ru.ifmo.eshop.tags.beans.PageParameter;

public class PutTag extends TagSupport {

    private String name, content, direct = "false";

    public void setName(String s) {
        name = s;
    }

    public void setContent(String s) {
        content = s;
    }

    public void setDirect(String s) {
        direct = s;
    }

    @Override
    public int doStartTag() throws JspException {
        InsertTag parent = (InsertTag) getAncestor(
                "ru.ifmo.eshop.tags.templates.InsertTag");
        if (parent == null) {
            throw new JspException("PutTag.doStartTag():No InsertTag ancestor");
        }
        Stack template_stack = parent.getStack();
        if (template_stack == null) {
            throw new JspException("PutTag: no template stack");
        }
        Hashtable params = (Hashtable) template_stack.peek();
        if (params == null) {
            throw new JspException("PutTag: no hashtable");
        }
        params.put(name, new PageParameter(content, direct));
        return SKIP_BODY;
    }

    @Override
    public void release() {
        name = content = direct = null;
    }

    private TagSupport getAncestor(String className)
            throws JspException {
        Class klass = null; //
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new JspException(ex.getMessage());
        }
        return (TagSupport) findAncestorWithClass(this, klass);
    }
}
