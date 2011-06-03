package ru.ifmo.eshop.tags.storage;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Label;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 * 03.06.2011
 */
public final class LabelsTag extends RecordsTag {
    private List<Label> labels;
    private int index=0;
    private boolean rating;

    public boolean isRating() {
        return rating;
    }

    public void setRating(boolean rating) {
        this.rating = rating;
    }

    public Label fetchLabel() {
        return labels.get(index++);
    }

    @Override
    public int doStartTag() throws JspException {
        StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
        try {
            if (rating) {
                labels=sm.getLabelsRating();
            } else {
                labels=sm.getLabels(start, end);
            }
        } catch (SQLException ex) {
            throw new JspException(ex);
        }
        index=0;
        if (labels.isEmpty()) {
            return SKIP_BODY;
        } else {
            return EVAL_BODY_INCLUDE;
        }
    }

    @Override
    public int doAfterBody() throws JspException {
        if (index<labels.size()) {
            return EVAL_BODY_AGAIN;
        } else {
            return EVAL_PAGE;
        }
    }

    @Override
    public void release() {
        super.release();
        labels=null;
    }
}
