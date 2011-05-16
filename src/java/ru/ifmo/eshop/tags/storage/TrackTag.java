package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import ru.ifmo.eshop.storage.Track;

public class TrackTag extends EntityTag {

    @Override
    public int doStartTag() throws JspException {
        RecordTag recordTag=(RecordTag) RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.RecordTag");
        if (recordTag==null) {
            throw new JspException("No parent RecordTag found");
        }
        Track t=(Track)recordTag.getRecord();
        String content;
        //TODO validation of attribute values?
        if (field.equals("id")) {
            content=String.valueOf(t.getId());
        } else if (field.equals("artistId")) {
            content=String.valueOf(t.getArtist().getId());
        } else if (field.equals("itemId")) {
            content=String.valueOf(t.getItem().getId());
        } else if (field.equals("title")) {
            content=t.getTitle();
        } else if (field.equals("trackNumber")) {
            content=String.valueOf(t.getTrackNumber());
        } else if (field.equals("composer")) {
            if (t.getComposer()==null) {
                content=defaultValue;
            } else {
                content=t.getComposer();
            }
        } else if (field.equals("duration")) {
            if (t.getDuration()==null) {
                content=defaultValue;
            } else {
                content=t.getDuration();
            }
        } else if (field.equals("isVideo")) {
            //TODO returned bool value
            if (t.isVideo) {
                content="True";
            } else {
                content="False";
            }
        } else {
            throw new JspException("TrackTag:Wrong field name");
        }
        if (content.length()>length) {
            content=content.substring(0,length)+"...";
        }
        try {
            pageContext.getOut().print(content);
            return SKIP_BODY;
        } catch (IOException ex) {
            throw new JspException(ex);
        }
    }

    @Override
    public void release() {
        field = null;
    }
}
