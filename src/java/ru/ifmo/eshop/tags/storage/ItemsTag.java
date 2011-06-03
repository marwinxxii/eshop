package ru.ifmo.eshop.tags.storage;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Item;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 * 02.06.2011
 */
public final class ItemsTag extends RecordsTag {
    private List<Item> items;
    private int index=0;
    private int artistId=-1;
    private int labelId=-1;

    public Item fetchItem() {
        return items.get(index++);
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        if (artistId<=0) throw new IllegalArgumentException("id is <=0");
        this.artistId = artistId;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        if (labelId<=0) throw new IllegalArgumentException("id is <=0");
        this.labelId = labelId;
    }

    @Override
    public int doStartTag() throws JspException {
        StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
        try {
            if (labelId!=-1) {
                items=sm.getItemsForLabel(labelId);
            } else if (artistId!=-1) {
                items=sm.getItemsForArtist(artistId);
            } else {
                items = sm.getItems(start, end);
            }
        } catch (SQLException ex) {
            throw new JspException(ex);
        }
        index=0;
        if (items.isEmpty()) {
            return SKIP_BODY;
        } else {
            return EVAL_BODY_INCLUDE;
        }
    }

    @Override
    public int doAfterBody() throws JspException {
        if (index<items.size()) {
            return EVAL_BODY_AGAIN;
        } else {
            return EVAL_PAGE;
        }
    }

    @Override
    public void release() {
        super.release();
        items=null;
    }
}
