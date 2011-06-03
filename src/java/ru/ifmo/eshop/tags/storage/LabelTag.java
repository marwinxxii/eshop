package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Label;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 * 18.04.2011
 */
public final class LabelTag extends RecordTag {

    @Override
    public int doStartTag() throws JspException {
        Label l;
        if (keyId==-1) {
            LabelsTag tag=(LabelsTag)RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.LabelsTag");
            if (tag==null) {
                throw new JspException("No parent LabelsTag found");
            }
            l=tag.fetchLabel();
        } else {
            StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
            try {
                l = sm.getLabel(keyId);
            } catch (SQLException ex) {
                throw new JspException(ex);
            }
        }
        if (l==null) {
            try {
                pageContext.getOut().print(message);
                return SKIP_BODY;
            } catch (IOException ex) {
                throw new JspException(ex);
            }
        }
        fieldMap.put("id", l.getId());
        fieldMap.put("title",l.getTitle());
        fieldMap.put("country",l.getCountry());
        return EVAL_BODY_INCLUDE;
    }
}
