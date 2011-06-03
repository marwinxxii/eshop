package ru.ifmo.eshop.tags.storage;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import ru.ifmo.eshop.storage.StorageManager;
import ru.ifmo.eshop.storage.Track;

/**
 *
 * @author alex
 * 02.06.2011
 */
public class TracksTag extends TagSupport {

    private int start=1;
    private int end=5;
    private List<Track> tracks;
    private int index=0;
    private int itemId=-1;
    private String message;

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        if (end<=0) throw new IllegalArgumentException("end<=0");
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        if (start<=0) throw new IllegalArgumentException("start<=0");
        this.start = start;
    }

    public Track fetchTrack() {
        return tracks.get(index++);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        if (itemId<=0) throw new IllegalArgumentException("id is <=0");
        this.itemId=itemId;
    }

    @Override
    public int doStartTag() throws JspException {
        StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
        try {
            if (itemId==-1) {
                tracks = sm.getTracks(start,end);
            } else {
                tracks=sm.getTracksForItem(itemId);
            }
        } catch (SQLException ex) {
            throw new JspException(ex);
        }
        index=0;
        if (tracks.isEmpty()) {
            return SKIP_BODY;
        } else {
            return EVAL_BODY_INCLUDE;
        }
    }

    @Override
    public int doAfterBody() throws JspException {
        if (index<tracks.size()) {
            return EVAL_BODY_AGAIN;
        } else {
            return EVAL_PAGE;
        }
    }

    @Override
    public void release() {
        tracks=null;
    }
}
