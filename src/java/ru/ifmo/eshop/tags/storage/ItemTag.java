package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import ru.ifmo.eshop.storage.Item;

public class ItemTag extends EntityTag {

    @Override
    public int doStartTag() throws JspException {
        RecordTag recordTag=(RecordTag) RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.RecordTag");
        if (recordTag==null) {
            throw new JspException("No parent RecordTag found");
        }
        Item i=(Item)recordTag.getRecord();
        String content;
        //TODO validation of attribute values?
        if (field.equals("id")) {
            content=String.valueOf(i.getId());
        } else if (field.equals("mediaType")) {
            content=i.getMediaType();
        } else if (field.equals("format")) {
            content=i.getFormat();
        } else if (field.equals("labelId")) {
            if (i.getLabel()!=null) {
                content=String.valueOf(i.getLabel().getId());
            } else {
                content=defaultValue;
            }
        } else if (field.equals("title")) {
            content=i.getTitle();
        } else if (field.equals("cover")) {
            if (i.getCover()==null) {
                content=defaultValue;
            } else {
                content=i.getCover();
            }
        } else if (field.equals("releaseDate")) {
            content=i.getReleaseDate().toString();//TODO date format
        } else {
            throw new JspException("ItemTag:Wrong field name");
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
