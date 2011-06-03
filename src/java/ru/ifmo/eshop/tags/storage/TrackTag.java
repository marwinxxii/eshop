package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.StorageManager;
import ru.ifmo.eshop.storage.Track;

public class TrackTag extends RecordTag {

    private int keyId=-1;
    private String message;

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int id) {
        if (id<=0) throw new IllegalArgumentException("id is <=0");
        this.keyId=id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int doStartTag() throws JspException {
        Track t;
        if (keyId==-1) {
            TracksTag tag=(TracksTag)RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.TracksTag");
            if (tag==null) {
                throw new JspException("No parent ItemsTag found");
            }
            t=tag.fetchTrack();
        } else {
            StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
            try {
                t = sm.getTrack(keyId);
            } catch (SQLException ex) {
                throw new JspException(ex);
            }
        }
        if (t==null) {
            try {
                pageContext.getOut().print(message);
                return SKIP_BODY;
            } catch (IOException ex) {
                throw new JspException(ex);
            }
        }
        fieldMap.put("id", t.getId());
        fieldMap.put("artist.id",t.getArtist().getId());
        fieldMap.put("artist.title",t.getArtist().getTitle());
        fieldMap.put("item.id",t.getItem().getId());
        fieldMap.put("item.title",t.getItem().getTitle());
        fieldMap.put("title",t.getTitle());
        fieldMap.put("trackNumber",t.getTrackNumber());
        fieldMap.put("composer", t.getComposer());
        fieldMap.put("duration",t.getDuration());
        fieldMap.put("isVideo", t.isVideo);
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public void release() {
        message = null;
    }
}
