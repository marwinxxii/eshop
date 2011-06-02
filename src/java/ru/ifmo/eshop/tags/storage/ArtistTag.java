package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import ru.ifmo.eshop.storage.Artist;

/**
 *
 * @author alex
 */
public class ArtistTag extends EntityTag {

    @Override
    public int doStartTag() throws JspException {
        RecordTag recordTag=(RecordTag) RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.RecordTag");
        if (recordTag==null) {
            throw new JspException("No parent RecordTag found");
        }
        Artist a=(Artist)recordTag.getRecord();
        String content;
        //TODO validation of attribute values?
        if (field.equals("id")) {
            content=String.valueOf(a.getId());
        } else if (field.equals("title")) {
            content=a.getTitle();
        } else if (field.equals("genreId")) {//TODO rename
            content=String.valueOf(a.getGenre().getId());
        } else if (field.equals("country")) {
            if (a.getCountry()==null) {
                content=defaultValue;
            } else {
                content=a.getCountry();
            }
        } else if (field.equals("beginYear")) {
            if (a.getBeginYear()==null) {
                content=defaultValue;
            } else {
                content=String.valueOf(a.getBeginYear());
            }
        } else if (field.equals("endYear")) {
            if (a.getEndYear()==null) {
                content=defaultValue;
            } else {
                content=String.valueOf(a.getEndYear());
            }
        //TODO think about requesting genre fields in another way
        } else if (field.equals("genre.title")) {
            content=a.getGenre().getTitle();
        } else {
            throw new JspException("ArtistTag:Wrong field name");
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
